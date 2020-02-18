package lt.lukasnakas.service;

import lt.lukasnakas.exception.AccountNotFoundException;
import lt.lukasnakas.model.Account;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

	private final List<BankingService> bankingServices;

	public AccountService(List<BankingService> bankingServices) {
		this.bankingServices = bankingServices;
	}

	public Map<String, Account> getAccountMap() {
		return bankingServices.stream()
				.map(BankingService::retrieveAccounts)
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(Account::getId, account -> account));
	}

	public Account getAccountById(String id) {
		return Optional.ofNullable(getAccountMap().get(id))
				.orElseThrow(() -> new AccountNotFoundException(
						String.format("Account [id: %s] could not be found", id)));
	}

}
