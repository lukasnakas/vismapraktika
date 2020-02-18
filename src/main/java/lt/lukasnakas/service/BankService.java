package lt.lukasnakas.service;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.exception.AccountNotFoundException;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.TransactionNotFoundException;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankService {

	private final List<BankingService> bankingServices;

	public BankService(List<BankingService> bankingServices) {
		this.bankingServices = bankingServices;
	}

	public Map<String, Account> getAccountList() {
		return bankingServices.stream()
				.map(BankingService::retrieveAccounts)
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(Account::getId, account -> account));
	}

	public Account getAccountById(String id) {
		return Optional.ofNullable(getAccountList().get(id))
				.orElseThrow(() -> new AccountNotFoundException(
						String.format("Account [id: %s] could not be found", id)));
	}

	public Map<String, Transaction> getTransactionList() {
		return bankingServices.stream()
				.map(BankingService::retrieveTransactions)
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(Transaction::getId, transaction -> transaction));
	}

	public Transaction getTransactionById(String id) {
		return Optional.ofNullable(getTransactionList().get(id))
				.orElseThrow(() -> new TransactionNotFoundException(
						String.format("Transaction [id: %s] could not be found", id)));
	}

	private List<Transaction> getChosenBankingServiceList(Payment payment, String bankName) {
		return bankingServices.stream()
				.filter(bankingService -> bankNameMatches(bankName, bankingService.getBankName()))
				.map(bankingService -> bankingService.executeTransactionIfValid(payment))
				.collect(Collectors.toList());
	}

	public Transaction postTransaction(Payment payment, String bankName) {
		List<Transaction> transactionList = getChosenBankingServiceList(payment, bankName);

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