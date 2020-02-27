package lt.lukasnakas.service;

import lt.lukasnakas.model.CommonAccount;

import java.util.List;

public interface IAccountService {
	List<CommonAccount> retrieveAccounts();
}
