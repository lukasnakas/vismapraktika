package lt.lukasnakas.test.mapper;

import lt.lukasnakas.mapper.PaymentMapper;
import lt.lukasnakas.mapper.PaymentMapperImpl;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutReceiver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentMapperTest {

    private PaymentMapper paymentMapper = new PaymentMapperImpl();

    @Test
    public void paymentDtoToPayment_shouldReturnTrue_whenPaymentsMatch() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setSenderAccountId("123");
        paymentDTO.setReceiverAccountId("456");
        paymentDTO.setDescription("desc");
        paymentDTO.setCurrency("EUR");
        paymentDTO.setCounterpartyId("789");
        paymentDTO.setAmount(100);
        paymentDTO.setBankName("danske");

        Payment expected = new Payment();
        expected.setSenderAccountId("123");
        expected.setReceiverAccountId("456");
        expected.setDescription("desc");
        expected.setCurrency("EUR");
        expected.setCounterpartyId("789");
        expected.setAmount(100);
        expected.setBankName("danske");

        Payment actual = paymentMapper.paymentDtoToPayment(paymentDTO);

        assertEquals(expected, actual);
    }

    @Test
    public void paymentToPaymentDto_shouldReturnTrue_whenPaymentDtosMatch() {
        Payment payment = new Payment();
        payment.setSenderAccountId("123");
        payment.setReceiverAccountId("456");
        payment.setDescription("desc");
        payment.setCurrency("EUR");
        payment.setCounterpartyId("789");
        payment.setAmount(100);
        payment.setBankName("danske");

        PaymentDTO expected = new PaymentDTO();
        expected.setSenderAccountId("123");
        expected.setReceiverAccountId("456");
        expected.setDescription("desc");
        expected.setCurrency("EUR");
        expected.setCounterpartyId("789");
        expected.setAmount(100);
        expected.setBankName("danske");

        PaymentDTO actual = paymentMapper.paymentToPaymentDto(payment);

        assertEquals(expected, actual);
    }

    @Test
    public void paymentToRevolutPayment_shouldReturnTrue_whenPaymentsMatch() {
        Payment payment = new Payment();
        payment.setSenderAccountId("123");
        payment.setReceiverAccountId("456");
        payment.setDescription("desc");
        payment.setCurrency("EUR");
        payment.setCounterpartyId("789");
        payment.setAmount(100);

        RevolutReceiver revolutReceiver = new RevolutReceiver();
        RevolutPayment expected = new RevolutPayment();

        revolutReceiver.setCounterPartyId("789");
        revolutReceiver.setAccountId("456");

        expected.setAccountId("123");
        expected.setReceiver(revolutReceiver);
        expected.setCurrency("EUR");
        expected.setReference("desc");
        expected.setAmount(100);

        RevolutPayment actual = paymentMapper.paymentToRevolutPayment(payment);

        assertEquals(expected, actual);
    }

}
