package lt.lukasnakas.jms;

import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.dto.PaymentDTO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination = "inbound.queue", containerFactory = "myFactory")
    public void receiveMessage(PaymentDTO paymentDTO) {
        System.out.println("Received <" + paymentDTO + ">");
    }

}
