package lt.lukasnakas.service;

import lt.lukasnakas.mapper.PaymentMapper;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.TransactionNotFoundException;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.repository.PaymentRepository;
import lt.lukasnakas.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
	private final List<BankingService> bankingServices;
	private final TransactionRepository transactionRepository;
	private final PaymentRepository paymentRepository;
	private final PaymentMapper paymentMapper;

	public TransactionService(List<BankingService> bankingServices,
							  TransactionRepository transactionRepository,
							  PaymentRepository paymentRepository,
							  PaymentMapper paymentMapper) {
		this.bankingServices = bankingServices;
		this.transactionRepository = transactionRepository;
		this.paymentRepository = paymentRepository;
		this.paymentMapper = paymentMapper;
	}

	public List<CommonTransaction> getTransactions() {
		return (List<CommonTransaction>) transactionRepository.findAll();
	}

	public CommonTransaction getTransactionById(String id) {
		Optional<CommonTransaction> transaction = transactionRepository.findById(id);

		if (transaction.isPresent()) {
			return transaction.get();
		}
		throw new TransactionNotFoundException(String.format("Transaction [id: %s] not found", id));
	}

	public List<CommonTransaction> updateTransactions() {
		List<CommonTransaction> commonTransactionList = bankingServices.stream()
				.map(BankingService::retrieveTransactions)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());

		return (List<CommonTransaction>) transactionRepository.saveAll(commonTransactionList);
	}

	private CommonTransaction getChosenBankingServiceForPost(Payment payment, String bankName) {
		return bankingServices.stream()
				.filter(bankingService -> bankNameMatches(bankName, bankingService.getBankName()))
				.map(bankingService -> bankingService.executeTransactionIfValid(payment))
				.findAny()
				.orElseThrow(() -> new BadRequestException(new TransactionError("bankName").getMessage()));
	}

	public CommonTransaction postTransaction(PaymentDTO paymentDTO, String bankName) {
		Payment payment = paymentMapper.paymentDtoToPayment(paymentDTO);
		CommonTransaction transaction = getChosenBankingServiceForPost(payment, bankName);

		paymentRepository.save(payment);
		return transactionRepository.save(transaction);
	}

	private boolean bankNameMatches(String bankName, String bankingServiceBankName) {
		return bankName.equalsIgnoreCase(bankingServiceBankName);
	}

}
