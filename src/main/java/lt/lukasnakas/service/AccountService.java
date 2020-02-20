package lt.lukasnakas.service;

import lt.lukasnakas.exception.AccountNotFoundException;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.repository.AccountRepository;
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

    public List<CommonAccount> getAccounts() {
        return (List<CommonAccount>) accountRepository.findAll();
    }

    public CommonAccount getAccountById(String id) {
        Optional<CommonAccount> account = accountRepository.findById(id);

        if (account.isPresent()) {
            return account.get();
        }
        throw new AccountNotFoundException(String.format("Account [id: %s] not found", id));
    }

    public List<CommonAccount> updateAccounts() {
        List<CommonAccount> commonAccountList = bankingServices.stream()
                .map(BankingService::retrieveAccounts)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return (List<CommonAccount>) accountRepository.saveAll(commonAccountList);
    }

}