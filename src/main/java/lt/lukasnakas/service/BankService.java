package lt.lukasnakas.service;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Bank;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.danske.DanskeTransaction;
import lt.lukasnakas.model.revolut.RevolutTransaction;
import lt.lukasnakas.service.danske.DanskeService;
import lt.lukasnakas.service.revolut.RevolutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {

	@Autowired
	private DanskeService danskeService;

	@Autowired
	private RevolutService revolutService;

	public AccountService getAccountService(String bankName){
		if(bankExists(bankName)) {
			Bank bank = Bank.valueOf(bankName.toUpperCase());

			if (bank == Bank.DANSKE)
				return danskeService;
			else if (bank == Bank.REVOLUT)
				return revolutService;
		}
		return null;
	}

	public List<Account> getAccounts(){
		List<Account> accountsList = new ArrayList<>();
		accountsList.addAll(danskeService.retrieveAccounts());
		accountsList.addAll(revolutService.retrieveAccounts());
		return accountsList;
	}

	public List<Transaction> getTransactions(){
		List<Transaction> transactionsList = new ArrayList<>();
		transactionsList.addAll(danskeService.retrieveTransactions());
		transactionsList.addAll(revolutService.retrieveTransactions());
		return transactionsList;
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
