package lt.lukasnakas.service;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BankService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankService.class);

    private final BankingService[] bankingServices;

    public BankService(BankingService[] bankingServices) {
        this.bankingServices = bankingServices;
    }

    private List<Account> getAllAccountsList() {
        List<Account> accountsList = new ArrayList<>();

        for (BankingService bankingService : bankingServices) {
            accountsList.addAll(bankingService.retrieveAccounts());
        }

        return accountsList;
    }

    public Map<String, Account> getAccounts() {
        return getAllAccountsList().stream()
                .collect(Collectors.toMap(Account::getId, account -> account));
    }

    public Account getAccountById(String id) {
        return getAccounts().get(id);
    }

    private List<Transaction> getAllTransactionsList() {
        List<Transaction> transactionsList = new ArrayList<>();

        for (BankingService bankingService : bankingServices) {
            transactionsList.addAll(bankingService.retrieveTransactions());
        }

        return transactionsList;
    }

    public Map<String, Transaction> getTransactions() {
        return getAllTransactionsList().stream()
                .collect(Collectors.toMap(Transaction::getId, transaction -> transaction));
    }

    public Transaction getTransactionById(String id) {
        return getTransactions().get(id);
    }

    public Transaction postTransaction(Payment payment, String bankName) {
        for (BankingService bankingService : bankingServices) {
            if (bankName.equalsIgnoreCase(bankingService.getBankName())) {
                return bankingService.executeTransactionIfValid(payment);
            }
        }
        return getTransactionError("bankName");
    }

    private TransactionError getTransactionError(String invalidKey) {
        TransactionError transactionError = new TransactionError(invalidKey);
        String errorMsg = transactionError.toString();
        LOGGER.error(errorMsg);
        return transactionError;
    }
}