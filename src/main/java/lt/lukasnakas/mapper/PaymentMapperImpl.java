package lt.lukasnakas.mapper;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutReceiver;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment paymentDtoToPayment(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }

        Payment payment = new Payment();

        payment.setId(paymentDTO.getId());
        payment.setSenderAccountId(paymentDTO.getSenderAccountId());
        payment.setReceiverAccountId(paymentDTO.getReceiverAccountId());
        payment.setDescription(paymentDTO.getDescription());
        payment.setCurrency(paymentDTO.getCurrency());
        payment.setCounterpartyId(paymentDTO.getCounterpartyId());
        payment.setAmount(paymentDTO.getAmount());
        payment.setBankName(paymentDTO.getBankName());

        return payment;
    }

    @Override
    public PaymentDTO paymentToPaymentDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setId(payment.getId());
        paymentDTO.setSenderAccountId(payment.getSenderAccountId());
        paymentDTO.setReceiverAccountId(payment.getReceiverAccountId());
        paymentDTO.setDescription(payment.getDescription());
        paymentDTO.setCurrency(payment.getCurrency());
        paymentDTO.setCounterpartyId(payment.getCounterpartyId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setBankName(payment.getBankName());

        return paymentDTO;
    }

    @Override
    public RevolutPayment paymentToRevolutPayment(Payment payment) {
        if (payment == null) {
            return null;
        }

        RevolutReceiver revolutReceiver = new RevolutReceiver();
        RevolutPayment revolutPayment = new RevolutPayment();

        revolutReceiver.setCounterPartyId(payment.getCounterpartyId());
        revolutReceiver.setAccountId(payment.getReceiverAccountId());

        revolutPayment.setAccountId(payment.getSenderAccountId());
        revolutPayment.setReceiver(revolutReceiver);
        revolutPayment.setCurrency(payment.getCurrency());
        revolutPayment.setReference(payment.getDescription());
        revolutPayment.setAmount(payment.getAmount());
        revolutPayment.setGeneratedRequestId();

        return revolutPayment;
    }

    @Override
    public RevolutPayment paymentDtoToRevolutPayment(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }

        RevolutReceiver revolutReceiver = new RevolutReceiver();
        RevolutPayment revolutPayment = new RevolutPayment();

        revolutReceiver.setCounterPartyId(paymentDTO.getCounterpartyId());
        revolutReceiver.setAccountId(paymentDTO.getReceiverAccountId());

        revolutPayment.setAccountId(paymentDTO.getSenderAccountId());
        revolutPayment.setReceiver(revolutReceiver);
        revolutPayment.setCurrency(paymentDTO.getCurrency());
        revolutPayment.setReference(paymentDTO.getDescription());
        revolutPayment.setAmount(paymentDTO.getAmount());
        revolutPayment.setGeneratedRequestId();

        return revolutPayment;
    }
}
