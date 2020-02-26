package lt.lukasnakas.test;

import lt.lukasnakas.jms.Consumer;
import lt.lukasnakas.jms.Producer;
import lt.lukasnakas.model.dto.PaymentDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsTest {

	@Autowired
	private Producer producer;

	@Autowired
	private Consumer consumer;

	@Test
	public void testReceive() throws Exception {
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setSenderAccountId("123");
		paymentDTO.setReceiverAccountId("456");
		paymentDTO.setCounterpartyId("789");
		paymentDTO.setAmount(10);
		paymentDTO.setCurrency("EUR");
		paymentDTO.setDescription("desc");

		producer.send(paymentDTO);
	}

}
