package lt.lukasnakas.service;

import lt.lukasnakas.model.Bank;
import lt.lukasnakas.service.danske.DanskeAccountService;
import lt.lukasnakas.service.revolut.RevolutAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {

	@Autowired
	private DanskeAccountService danskeAccountService;

	@Autowired
	private RevolutAccountService revolutAccountService;

	public AccountService getAccountService(String bankName){
		if(bankExists(bankName)) {
			Bank bank = Bank.valueOf(bankName.toUpperCase());

			if (bank == Bank.DANSKE)
				return danskeAccountService;
			else if (bank == Bank.REVOLUT)
				return revolutAccountService;
		}
		return null;
	}

	private boolean bankExists(String bankName){
		for(Bank bank : Bank.values())
			if (bank.name().equalsIgnoreCase(bankName))
				return true;
		return false;
	}

}
