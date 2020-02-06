package lt.lukasnakas.Service;

import lt.lukasnakas.Model.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    String retrieveAccounts();
    List<Account> getParsedAccounts(String accounts);
}
