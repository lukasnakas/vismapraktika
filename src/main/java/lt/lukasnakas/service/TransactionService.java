package lt.lukasnakas.service;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import java.util.List;

public interface TransactionService {
	List<Transaction> retrieveTransactions();
	Transaction postTransaction(Payment payment);
	List<Transaction> getParsedTransactionsList(List<? extends Transaction> unparsedTransactionsList);
}
