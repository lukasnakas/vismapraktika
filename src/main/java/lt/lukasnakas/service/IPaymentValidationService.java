package lt.lukasnakas.service;

import lt.lukasnakas.model.Payment;

public interface IPaymentValidationService {
    boolean isValid(Payment payment);
}
