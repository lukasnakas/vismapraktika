package lt.lukasnakas.service;

import lt.lukasnakas.mapper.ITransactionMapper;
import lt.lukasnakas.exception.TransactionNotFoundException;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.dto.CommonTransactionDTO;
import lt.lukasnakas.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final List<ITransactionService> transactionServices;
    private final TransactionRepository transactionRepository;
    private final ITransactionMapper transactionMapper;

    public TransactionService(List<ITransactionService> transactionServices,
                              TransactionRepository transactionRepository,
                              ITransactionMapper transactionMapper) {
        this.transactionServices = transactionServices;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public List<CommonTransactionDTO> getTransactions() {
        return getMappedTransactions(transactionRepository.findAll());
    }

    public CommonTransactionDTO getTransactionById(String id) {
        Optional<CommonTransaction> transaction = transactionRepository.findById(id);

        if (transaction.isPresent()) {
            return transactionMapper.commonTransactionToCommonTransactionDto(transaction.get());
        }
        throw new TransactionNotFoundException(String.format("Transaction [id: %s] not found", id));
    }

    public List<CommonTransactionDTO> updateTransactions() {
        List<CommonTransaction> commonTransactionList = transactionServices.stream()
                .map(ITransactionService::retrieveTransactions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return getMappedTransactions((List<CommonTransaction>) transactionRepository.saveAll(commonTransactionList));
    }

    private List<CommonTransactionDTO> getMappedTransactions(List<CommonTransaction> commonTransactionList) {
        return commonTransactionList.stream()
                .map(transactionMapper::commonTransactionToCommonTransactionDto)
                .collect(Collectors.toList());
    }

}
