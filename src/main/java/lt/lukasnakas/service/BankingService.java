package lt.lukasnakas.service;

import lt.lukasnakas.model.*;

import java.util.List;

public interface BankingService {
    List<CommonAccount> retrieveAccounts();

    List<Account> getParsedAccountsList(List<? extends Account> unparsedAccountsList);

    List<CommonTransaction> retrieveTransactions();

    CommonTransaction postTransaction(Payment payment);

    List<Transaction> getParsedTransactionsList(List<? extends Transaction> unparsedTransactionsList);

    CommonTransaction executeTransactionIfValid(Payment payment);

    boolean isPaymentValid(Payment payment);

    String getBankName();
}
