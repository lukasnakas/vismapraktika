package lt.lukasnakas.Service;

import lt.lukasnakas.Model.Account;

import java.util.List;

public interface AccountService {
    List<Account> retrieveAccounts() throws Exception;
    List<Account> getParsedAccounts(String accounts);
}
