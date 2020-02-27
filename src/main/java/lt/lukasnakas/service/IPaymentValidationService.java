package lt.lukasnakas.service;

import lt.lukasnakas.model.dto.PaymentDTO;

public interface IPaymentValidationService {
    boolean isValid(PaymentDTO paymentDTO);
}
