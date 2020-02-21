package lt.lukasnakas.service;

import lt.lukasnakas.model.*;

import java.util.List;

public interface BankingService {
    List<CommonAccount> retrieveAccounts();

    List<CommonTransaction> retrieveTransactions();

    CommonTransaction postTransaction(Payment payment);

    CommonTransaction executeTransactionIfValid(Payment payment);

    boolean isPaymentValid(Payment payment);

    String getBankName();
}
