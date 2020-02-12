package lt.lukasnakas.controller;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class BankController {

	@Autowired
	private BankService bankService;

	@GetMapping(value = "/accounts", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<Account>> getAllAccounts(){
		return new ResponseEntity<>(bankService.getAccounts(), HttpStatus.OK);
	}

	@GetMapping(value = "/transactions", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<Transaction>> getAllTransactions(){
		return new ResponseEntity<>(bankService.getTransactions(), HttpStatus.OK);
	}

	@PostMapping(value = "/transactions", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Transaction> addTransaction(@RequestBody String paymentBody){
		return new ResponseEntity<>(bankService.postTransaction(paymentBody), HttpStatus.OK);
	}

}
