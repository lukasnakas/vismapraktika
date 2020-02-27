package lt.lukasnakas.service;

import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.dto.PaymentDTO;

public interface IPaymentService {

	CommonTransaction postPayment(Payment payment);

	String getBankName();

	CommonTransaction executePaymentIfValid(PaymentDTO paymentDTO);

}
