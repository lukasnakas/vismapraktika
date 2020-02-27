package lt.lukasnakas.jms;

import lt.lukasnakas.model.dto.PaymentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import static lt.lukasnakas.configuration.JmsConfiguration.PAYMENT_QUEUE;

@Service
public class Producer {
	private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

	private final JmsTemplate jmsTemplate;

	public Producer(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public PaymentDTO send(PaymentDTO paymentDTO) {
		LOGGER.info("sending message='{}' to queue='{}'", paymentDTO, PAYMENT_QUEUE);
		jmsTemplate.convertAndSend(PAYMENT_QUEUE, paymentDTO);
		return paymentDTO;
	}

}
