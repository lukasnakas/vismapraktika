package lt.lukasnakas.jms;

import lt.lukasnakas.mapper.PaymentMapper;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.PaymentStatus;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.repository.PaymentRepository;
import lt.lukasnakas.repository.TransactionRepository;
import lt.lukasnakas.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import static lt.lukasnakas.configuration.JmsConfiguration.PAYMENT_QUEUE;

@Service
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentService paymentService;

    public Consumer(PaymentMapper paymentMapper,
                    PaymentRepository paymentRepository,
                    TransactionRepository transactionRepository,
                    PaymentService paymentService) {
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
        this.transactionRepository = transactionRepository;
        this.paymentService = paymentService;
    }

    @JmsListener(destination = PAYMENT_QUEUE)
    public void receive(PaymentDTO paymentDTO) {
        LOGGER.info("received message='{}' from queue='{}'", paymentDTO, PAYMENT_QUEUE);

        CommonTransaction commonTransaction;
        Payment payment = paymentMapper.paymentDtoToPayment(paymentDTO);

        try {
            commonTransaction = paymentService.getExecutedPaymentAsCommonTransaction(paymentDTO);
            transactionRepository.save(commonTransaction);
            payment.setPaymentStatus(PaymentStatus.COMPLETED.getValue());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            payment.setPaymentStatus(PaymentStatus.FAILED.getValue());
        }

        paymentRepository.save(payment);
    }

}
