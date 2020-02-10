package lt.lukasnakas.service;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Bank;
import lt.lukasnakas.model.danske.DanskeTransaction;
import lt.lukasnakas.service.danske.DanskeService;
import lt.lukasnakas.service.revolut.RevolutAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

	@Autowired
	private DanskeService danskeService;

	@Autowired
	private RevolutAccountService revolutAccountService;

	public AccountService getAccountService(String bankName){
		if(bankExists(bankName)) {
			Bank bank = Bank.valueOf(bankName.toUpperCase());

			if (bank == Bank.DANSKE)
				return danskeService;
			else if (bank == Bank.REVOLUT)
				return revolutAccountService;
		}
		return null;
	}

	public List<Account> getAccounts(){
		List<Account> accountsList = danskeService.retrieveAccounts();
		accountsList.addAll(revolutAccountService.retrieveAccounts());
		return accountsList;
	}

	public List<DanskeTransaction> getDanskeTransactions(){
		return danskeService.retrieveTransactions();
	}

	public DanskeTransaction postDanskeTransaction(DanskeTransaction danskeTransaction){
		return danskeService.postTransaction(danskeTransaction);
	}

	private boolean bankExists(String bankName){
		for(Bank bank : Bank.values())
			if (bank.name().equalsIgnoreCase(bankName))
				return true;
		return false;
	}

}
