package lt.lukasnakas.service;

import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.PaymentNotFoundException;
import lt.lukasnakas.jms.Producer;
import lt.lukasnakas.mapper.PaymentMapper;
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

	private final PaymentRepository paymentRepository;
	private final PaymentMapper paymentMapper;
	private final Producer producer;

	public PaymentService(PaymentRepository paymentRepository,
						  PaymentMapper paymentMapper,
						  Producer producer) {
		this.paymentRepository = paymentRepository;
		this.paymentMapper = paymentMapper;
		this.producer = producer;
	}

	public List<PaymentDTO> getPayments() {
		return ((List<Payment>) paymentRepository.findAll()).stream()
				.map(paymentMapper::paymentToPaymentDto)
				.collect(Collectors.toList());
	}

	public PaymentDTO postTransaction(PaymentDTO paymentDTO) {
		if(paymentDTO.getBankName() != null) {
			paymentDTO.setStatus(PaymentStatus.IN_QUEUE.getValue());
			Payment payment = paymentRepository.save(paymentMapper.paymentDtoToPayment(paymentDTO));
			return producer.send(paymentMapper.paymentToPaymentDto(payment));
		} else {
			throw new BadRequestException(new TransactionError("bankName").getMessage());
		}
	}

	public PaymentDTO getPaymentStatus(String id) {
		long paymentId = Long.parseLong(id);

		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new PaymentNotFoundException(String.format("Payment [id: %s] not found", id)));

		return paymentMapper.paymentToPaymentDto(payment);
	}

}
