package lt.lukasnakas.service;

import lt.lukasnakas.model.*;
import lt.lukasnakas.model.dto.PaymentDTO;

import java.util.List;

public interface BankingService {
    List<CommonAccount> retrieveAccounts();

    List<CommonTransaction> retrieveTransactions();

    CommonTransaction postTransaction(Payment payment);

    CommonTransaction executeTransactionIfValid(PaymentDTO paymentDTO);

    boolean isPaymentValid(Payment payment);

    String getBankName();
}
