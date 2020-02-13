package lt.lukasnakas.service;

import lt.lukasnakas.model.Payment;

public interface PaymentValidationService {
	boolean isValid(Payment payment);
}
