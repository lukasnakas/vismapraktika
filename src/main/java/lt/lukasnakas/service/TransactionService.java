package lt.lukasnakas.service;

import lt.lukasnakas.mapper.PaymentMapper;
import lt.lukasnakas.mapper.TransactionMapper;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.TransactionNotFoundException;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.dto.CommonTransactionDTO;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.repository.PaymentRepository;
import lt.lukasnakas.repository.TransactionRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final List<BankingService> bankingServices;
    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final TransactionMapper transactionMapper;
    private final JmsTemplate jmsTemplate;

    public TransactionService(List<BankingService> bankingServices,
                              TransactionRepository transactionRepository,
                              PaymentRepository paymentRepository,
                              PaymentMapper paymentMapper,
                              TransactionMapper transactionMapper,
                              JmsTemplate jmsTemplate) {
        this.bankingServices = bankingServices;
        this.transactionRepository = transactionRepository;
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.transactionMapper = transactionMapper;
        this.jmsTemplate = jmsTemplate;
    }

    public List<CommonTransactionDTO> getTransactions() {
        return ((List<CommonTransaction>) transactionRepository.findAll()).stream()
                .map(transactionMapper::commonTransactionToCommonTransactionDto)
                .collect(Collectors.toList());
    }

    public CommonTransactionDTO getTransactionById(String id) {
        Optional<CommonTransaction> transaction = transactionRepository.findById(id);

        if (transaction.isPresent()) {
            return transactionMapper.commonTransactionToCommonTransactionDto(transaction.get());
        }
        throw new TransactionNotFoundException(String.format("Transaction [id: %s] not found", id));
    }

    public List<CommonTransactionDTO> updateTransactions() {
        List<CommonTransaction> commonTransactionList = bankingServices.stream()
                .map(BankingService::retrieveTransactions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return ((List<CommonTransaction>) transactionRepository.saveAll(commonTransactionList)).stream()
                .map(transactionMapper::commonTransactionToCommonTransactionDto)
                .collect(Collectors.toList());
    }

    private CommonTransaction getChosenBankingServiceForPost(Payment payment, String bankName) {
        return bankingServices.stream()
                .filter(bankingService -> bankNameMatches(bankName, bankingService.getBankName()))
                .map(bankingService -> bankingService.executeTransactionIfValid(payment))
                .findAny()
                .orElseThrow(() -> new BadRequestException(new TransactionError("bankName").getMessage()));
    }

    public CommonTransactionDTO postTransaction(PaymentDTO paymentDTO, String bankName) {
        Payment payment = paymentMapper.paymentDtoToPayment(paymentDTO);

        jmsTemplate.convertAndSend("inbound.queue", paymentDTO);

        CommonTransaction transaction = getChosenBankingServiceForPost(payment, bankName);

        paymentRepository.save(payment);
        return transactionMapper.commonTransactionToCommonTransactionDto(transactionRepository.save(transaction));
    }

    private boolean bankNameMatches(String bankName, String bankingServiceBankName) {
        return bankName.equalsIgnoreCase(bankingServiceBankName);
    }

}
