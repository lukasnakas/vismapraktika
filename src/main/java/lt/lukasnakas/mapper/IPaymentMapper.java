package lt.lukasnakas.mapper;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;

public interface IPaymentMapper {

    Payment paymentDtoToPayment(PaymentDTO paymentDTO);

    PaymentDTO paymentToPaymentDto(Payment payment);

    RevolutPayment paymentToRevolutPayment(Payment payment);

    RevolutPayment paymentDtoToRevolutPayment(PaymentDTO paymentDTO);

}
