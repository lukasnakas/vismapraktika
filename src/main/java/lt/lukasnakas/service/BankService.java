package lt.lukasnakas.service;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BankService {

    private final List<BankingService> bankingServices;

    public BankService(List<BankingService> bankingServices) {
        this.bankingServices = bankingServices;
    }

    private Stream<Account> getAllAccountsStream() {
        return bankingServices.stream()
                .map(BankingService::retrieveAccounts)
                .flatMap(Collection::stream);
    }

    public Map<String, Account> getAccounts() {
        return getAllAccountsStream()
                .collect(Collectors.toMap(Account::getId, account -> account));
    }

    public Account getAccountById(String id) {
        return getAccounts().get(id);
    }

    private Stream<Transaction> getAllTransactionsList() {
        return bankingServices.stream()
                .map(BankingService::retrieveTransactions)
                .flatMap(Collection::stream);
    }

    public Map<String, Transaction> getTransactions() {
        return getAllTransactionsList()
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
        throw new BadRequestException(new TransactionError("bankName").getMessage());
    }
}