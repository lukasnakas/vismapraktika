package lt.lukasnakas.service;

import lt.lukasnakas.model.CommonTransaction;

import java.util.List;

public interface ITransactionService {
	List<CommonTransaction> retrieveTransactions();
}
