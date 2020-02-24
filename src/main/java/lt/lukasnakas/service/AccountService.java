package lt.lukasnakas.service;

import lt.lukasnakas.exception.AccountNotFoundException;
import lt.lukasnakas.mapper.AccountMapper;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.dto.CommonAccountDTO;
import lt.lukasnakas.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final List<BankingService> bankingServices;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(List<BankingService> bankingServices,
                          AccountRepository accountRepository,
                          AccountMapper accountMapper) {
        this.bankingServices = bankingServices;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public List<CommonAccountDTO> getAccounts() {
        return ((List<CommonAccount>) accountRepository.findAll()).stream()
                .map(accountMapper::commonAccountToCommonAccountDto)
                .collect(Collectors.toList());
    }

    public CommonAccountDTO getAccountById(String id) {
        Optional<CommonAccount> account = accountRepository.findById(id);

        if (account.isPresent()) {
            return accountMapper.commonAccountToCommonAccountDto(account.get());
        }
        throw new AccountNotFoundException(String.format("Account [id: %s] not found", id));
    }

    public List<CommonAccountDTO> updateAccounts() {
        List<CommonAccount> commonAccountList = bankingServices.stream()
                .map(BankingService::retrieveAccounts)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return ((List<CommonAccount>) accountRepository.saveAll(commonAccountList)).stream()
                .map(accountMapper::commonAccountToCommonAccountDto)
                .collect(Collectors.toList());
    }

}