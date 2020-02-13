package lt.lukasnakas.controller;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountsController {

	@Autowired
	private BankService bankService;

	@GetMapping(value = "", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Account>> getAllAccounts() {
		return new ResponseEntity<>(bankService.getAccounts(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Account> getAccountById(@PathVariable String id){
		return new ResponseEntity<>(bankService.getAccountById(id), HttpStatus.OK);
	}

}
