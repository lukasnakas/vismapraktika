package lt.lukasnakas.mapper;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.dto.PaymentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper {

	Payment paymentDtoToPayment(PaymentDTO paymentDTO);

	PaymentDTO paymentToPaymentDto(Payment payment);

}
