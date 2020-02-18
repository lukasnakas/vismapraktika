package lt.lukasnakas.service;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.TransactionNotFoundException;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
	private final List<BankingService> bankingServices;

	public TransactionService(List<BankingService> bankingServices) {
		this.bankingServices = bankingServices;
	}

	public Map<String, Transaction> getTransactionMap() {
		return bankingServices.stream()
				.map(BankingService::retrieveTransactions)
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(Transaction::getId, transaction -> transaction));
	}

	public Transaction getTransactionById(String id) {
		return Optional.ofNullable(getTransactionMap().get(id))
				.orElseThrow(() -> new TransactionNotFoundException(
						String.format("Transaction [id: %s] could not be found", id)));
	}

	private List<Transaction> getChosenBankingServiceListForPost(Payment payment, String bankName) {
		return bankingServices.stream()
				.filter(bankingService -> bankNameMatches(bankName, bankingService.getBankName()))
				.map(bankingService -> bankingService.executeTransactionIfValid(payment))
				.collect(Collectors.toList());
	}

	public Transaction postTransaction(Payment payment, String bankName) {
		List<Transaction> transactionList = getChosenBankingServiceListForPost(payment, bankName);

		if (!transactionList.isEmpty()) {
			return transactionList.get(0);
		} else {
			throw new BadRequestException(new TransactionError("bankName").getMessage());
		}

	}

	private boolean bankNameMatches(String bankName, String bankingServiceBankName) {
		return bankName.equalsIgnoreCase(bankingServiceBankName);
	}
}