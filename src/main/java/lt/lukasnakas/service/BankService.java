package lt.lukasnakas.service;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BankService {

    private final List<BankingService> bankingServices;

    public BankService(List<BankingService> bankingServices) {
        this.bankingServices = bankingServices;
    }

    public Map<String, Account> getAccountList() {
        return bankingServices.stream()
                .map(BankingService::retrieveAccounts)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Account::getId, account -> account));
    }

    public Account getAccountById(String id) {
        return getAccountList().get(id);
    }

    public Map<String, Transaction> getTransactionList() {
        return bankingServices.stream()
                .map(BankingService::retrieveTransactions)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Transaction::getId, transaction -> transaction));
    }

    public Transaction getTransactionById(String id) {
        return getTransactionList().get(id);
    }

    public Transaction postTransaction(Payment payment, String bankName) {
        for (BankingService bankingService : bankingServices) {
            if (bankNameMatches(bankName, bankingService.getBankName())) {
                return bankingService.executeTransactionIfValid(payment);
            }
        }
        throw new BadRequestException(new TransactionError("bankName").getMessage());
    }

    private boolean bankNameMatches(String bankName, String bankingServiceBankName){
        return bankName.equalsIgnoreCase(bankingServiceBankName);
    }
}