package lt.lukasnakas.service;

import lt.lukasnakas.model.Account;

import java.util.List;

public interface AccountService {
	List<Account> retrieveAccounts();

	List<Account> getParsedAccountList(List<? extends Account> unparsedAccountsList);
}
