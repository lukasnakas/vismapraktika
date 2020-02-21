package lt.lukasnakas.service;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.TransactionNotFoundException;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.repository.PaymentRepository;
import lt.lukasnakas.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
	private final List<BankingService> bankingServices;
	private final TransactionRepository transactionRepository;
	private final PaymentRepository paymentRepository;

	public TransactionService(List<BankingService> bankingServices,
							  TransactionRepository transactionRepository,
							  PaymentRepository paymentRepository) {
		this.bankingServices = bankingServices;
		this.transactionRepository = transactionRepository;
		this.paymentRepository = paymentRepository;
	}

	public Map<String, CommonTransaction> getTransactionMap() {
		return bankingServices.stream()
				.map(BankingService::retrieveTransactions)
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(CommonTransaction::getId, transaction -> transaction));
	}

	public List<CommonTransaction> getTransactions() {
		return (List<CommonTransaction>) transactionRepository.findAll();
	}

	public CommonTransaction getTransactionById(String id) {
		Optional<CommonTransaction> transaction = transactionRepository.findById(id);

		if(transaction.isPresent()) {
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

	private List<CommonTransaction> getChosenBankingServiceListForPost(Payment payment, String bankName) {
		return bankingServices.stream()
				.filter(bankingService -> bankNameMatches(bankName, bankingService.getBankName()))
				.map(bankingService -> bankingService.executeTransactionIfValid(payment))
				.collect(Collectors.toList());
	}

	public CommonTransaction postTransaction(Payment payment, String bankName) {
		List<CommonTransaction> transactionList = getChosenBankingServiceListForPost(payment, bankName);

		if (!transactionList.isEmpty()) {
			paymentRepository.save(payment);
			return transactionRepository.save(transactionList.get(0));
		} else {
			throw new BadRequestException(new TransactionError("bankName").getMessage());
		}
	}

	private boolean bankNameMatches(String bankName, String bankingServiceBankName) {
		return bankName.equalsIgnoreCase(bankingServiceBankName);
	}
}
