package lt.lukasnakas.service;

import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.Payment;

public interface TransactionErrorService {
    TransactionError getErrorWithMissingParamsFromPayment(Payment payment);
}
