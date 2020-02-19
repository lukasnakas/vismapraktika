package lt.lukasnakas.service;

import lt.lukasnakas.exception.AccountNotFoundException;
import lt.lukasnakas.exception.TransactionNotFoundException;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.account.DanskeAccountDetails;
import lt.lukasnakas.repository.AccountRepository;
import lt.lukasnakas.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final List<BankingService> bankingServices;
    private final AccountRepository accountRepository;

    public AccountService(List<BankingService> bankingServices,
                          AccountRepository accountRepository) {
        this.bankingServices = bankingServices;
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    public Account getAccountById(String id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {
            return account.get();
        }
        throw new AccountNotFoundException(String.format("Account [id: %s] not found", id));
    }

    public Map<String, Account> getAccountMap() {
        return bankingServices.stream()
                .map(BankingService::retrieveAccounts)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Account::getId, account -> account));
    }

    public List<Account> updateAccounts() {
        List<Account> accs = bankingServices.stream()
                .map(BankingService::retrieveAccounts)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Account acc : accs) {
            if (acc instanceof DanskeAccount) {
                DanskeAccount dAcc = (DanskeAccount) acc;
                for (DanskeAccountDetails danskeAccountDetails : dAcc.getAccount()) {
                    danskeAccountDetails.setDanskeAccount(dAcc);
                }
            }
        }

        System.out.println(accs);
        return (List<Account>) accountRepository.saveAll(accs);
//        return bankingServices.stream()
//                .map(BankingService::retrieveAccounts)
//                .flatMap(Collection::stream)
//                .collect(Collectors.toMap(Account::getId, account -> account));
    }

//    public Account getAccountById(String id) {
//        return Optional.ofNullable(getAccountMap().get(id))
//                .orElseThrow(() -> new AccountNotFoundException(
//                        String.format("Account [id: %s] could not be found", id)));
//    }

}
