package lt.lukasnakas.service;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;

import java.util.List;

public interface BankingService {
    List<Account> retrieveAccounts();

    List<Account> getParsedAccountsList(List<? extends Account> unparsedAccountsList);

    List<Transaction> retrieveTransactions();

    Transaction postTransaction(Payment payment);

    List<Transaction> getParsedTransactionsList(List<? extends Transaction> unparsedTransactionsList);

    Transaction executeTransactionIfValid(Payment payment);

    boolean isPaymentValid(Payment payment);

    String getBankName();
}
