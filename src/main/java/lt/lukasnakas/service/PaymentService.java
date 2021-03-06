package lt.lukasnakas.service;

import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.BankTypeNotSupportedException;
import lt.lukasnakas.exception.InvalidIdException;
import lt.lukasnakas.exception.PaymentNotFoundException;
import lt.lukasnakas.jms.Producer;
import lt.lukasnakas.mapper.IPaymentMapper;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.PaymentStatus;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
	private final List<IPaymentService> paymentServices;
	private final PaymentRepository paymentRepository;
	private final IPaymentMapper paymentMapper;
	private final Producer producer;

	public PaymentService(List<IPaymentService> paymentServices,
						  PaymentRepository paymentRepository,
						  IPaymentMapper paymentMapper,
						  Producer producer) {
		this.paymentServices = paymentServices;
		this.paymentRepository = paymentRepository;
		this.paymentMapper = paymentMapper;
		this.producer = producer;
	}

	public List<PaymentDTO> getPayments() {
		return paymentRepository.findAll().stream()
				.map(paymentMapper::paymentToPaymentDto)
				.collect(Collectors.toList());
	}

	public PaymentDTO getPaymentById(String id) {
		long paymentId = parseStringToLong(id);

		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new PaymentNotFoundException(String.format("Payment [id: %s] not found", id)));

		return paymentMapper.paymentToPaymentDto(payment);
	}

	public PaymentDTO postTransaction(PaymentDTO paymentDTO) {
		if(paymentDTO.getBankName() != null) {
			IPaymentService paymentService = getPaymentServiceByBankName(paymentDTO.getBankName());

			if(paymentService.isPaymentBodyValid(paymentDTO)) {
				paymentDTO.setStatus(PaymentStatus.IN_QUEUE.getValue());
				Payment payment = paymentRepository.save(paymentMapper.paymentDtoToPayment(paymentDTO));

				return producer.send(paymentMapper.paymentToPaymentDto(payment));
			} else {
				throw new BadRequestException(paymentService.getTransactionErrorWithMissingParams(paymentDTO).getMessage());
			}
		} else {
			throw new BadRequestException(new TransactionError("bankName").getMessage());
		}
	}

	public CommonTransaction getExecutedPaymentAsCommonTransaction(PaymentDTO paymentDTO) {
		return paymentServices.stream()
				.filter(paymentService -> bankNameMatches(paymentDTO.getBankName(), paymentService.getBankName()))
				.map(bankingService -> bankingService.executePaymentIfValid(paymentDTO))
				.findAny()
				.orElseThrow(() -> new BadRequestException(new TransactionError("bankName").getMessage()));
	}

	private boolean bankNameMatches(String bankNameFromJson, String bankNameFromService) {
		return bankNameFromJson.equalsIgnoreCase(bankNameFromService);
	}

	private IPaymentService getPaymentServiceByBankName(String bankName) {
		return paymentServices.stream()
				.filter(paymentService -> bankNameMatches(bankName, paymentService.getBankName()))
				.findAny()
				.orElseThrow(() -> new BankTypeNotSupportedException(String.format("Bank '%s' not supported", bankName)));
	}

	private long parseStringToLong(String id) {
		try {
			return Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdException(String.format("ID: [%s] is not in valid format (numbers only)", id));
		}
	}

}
