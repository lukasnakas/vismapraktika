package lt.lukasnakas.service;

import lt.lukasnakas.jms.Producer;
import lt.lukasnakas.mapper.PaymentMapper;
import lt.lukasnakas.mapper.TransactionMapper;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.PaymentStatus;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.TransactionNotFoundException;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.dto.CommonTransactionDTO;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.repository.PaymentRepository;
import lt.lukasnakas.repository.TransactionRepository;
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
    private final TransactionMapper transactionMapper;
    private final PaymentMapper paymentMapper;
    private final Producer producer;

    public TransactionService(List<BankingService> bankingServices,
                              TransactionRepository transactionRepository,
                              PaymentRepository paymentRepository,
                              TransactionMapper transactionMapper,
                              PaymentMapper paymentMapper,
                              Producer producer) {
        this.bankingServices = bankingServices;
        this.transactionRepository = transactionRepository;
        this.paymentRepository = paymentRepository;
        this.transactionMapper = transactionMapper;
        this.paymentMapper = paymentMapper;
        this.producer = producer;
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

    public CommonTransaction getChosenBankingServiceForPost(PaymentDTO paymentDTO) {
        return bankingServices.stream()
                .filter(bankingService -> bankNameMatches(paymentDTO.getBankName(), bankingService.getBankName()))
                .map(bankingService -> bankingService.executeTransactionIfValid(paymentDTO))
                .findAny()
                .orElseThrow(() -> new BadRequestException(new TransactionError("bankName").getMessage()));
    }

    public PaymentDTO postTransaction(PaymentDTO paymentDTO) {
        if(paymentDTO.getBankName() != null) {
            paymentDTO.setStatus(PaymentStatus.IN_QUEUE.getValue());
            Payment payment = paymentRepository.save(paymentMapper.paymentDtoToPayment(paymentDTO));
            return producer.send(paymentMapper.paymentToPaymentDto(payment));
        } else {
            throw new BadRequestException(new TransactionError("bankName").getMessage());
        }
    }

    private boolean bankNameMatches(String bankName, String bankingServiceBankName) {
        return bankName.equalsIgnoreCase(bankingServiceBankName);
    }

}
