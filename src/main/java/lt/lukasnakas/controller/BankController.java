package lt.lukasnakas.controller;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.danske.DanskeTransaction;
import lt.lukasnakas.model.revolut.RevolutTransaction;
import lt.lukasnakas.service.AccountService;
import lt.lukasnakas.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankController {

	@Autowired
	private BankService bankService;

	@GetMapping(value = "/{bankName}/accounts", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<Account>> getAccounts(@PathVariable String bankName){
		AccountService accountService = bankService.getAccountService(bankName);
		if(accountService != null)
			return new ResponseEntity<>(accountService.retrieveAccounts(), HttpStatus.OK);
		return null;
	}

	@GetMapping(value = "/accounts", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<Account>> getAllAccounts(){
		return new ResponseEntity<>(bankService.getAccounts(), HttpStatus.OK);
	}

	@GetMapping(value = "/transactions", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<Transaction>> getAllTransactions(){
		return new ResponseEntity<>(bankService.getTransactions(), HttpStatus.OK);
	}

	@PostMapping(value = "/transactions", consumes = "application/json", produces = "application/json")
	public ResponseEntity<DanskeTransaction> addTransaction(@RequestBody DanskeTransaction danskeTransaction){
		return new ResponseEntity<>(bankService.postDanskeTransaction(danskeTransaction), HttpStatus.OK);
	}

}
