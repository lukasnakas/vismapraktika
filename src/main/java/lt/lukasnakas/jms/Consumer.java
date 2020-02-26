package lt.lukasnakas.jms;

import lt.lukasnakas.mapper.PaymentMapper;
import lt.lukasnakas.mapper.TransactionMapper;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.repository.PaymentRepository;
import lt.lukasnakas.repository.TransactionRepository;
import lt.lukasnakas.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    private final TransactionService transactionService;
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    public Consumer(TransactionService transactionService,
                    PaymentMapper paymentMapper,
                    PaymentRepository paymentRepository,
                    TransactionMapper transactionMapper,
                    TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
    }

    @JmsListener(destination = "inbound.queue")
    public void receive(PaymentDTO paymentDTO) {
        LOGGER.info("received message='{}'", paymentDTO);

        CommonTransaction transaction = transactionService.getChosenBankingServiceForPost(paymentDTO);
        Payment payment = paymentMapper.paymentDtoToPayment(paymentDTO);

        paymentRepository.save(payment);
        transactionMapper.commonTransactionToCommonTransactionDto(transactionRepository.save(transaction));
    }

}
