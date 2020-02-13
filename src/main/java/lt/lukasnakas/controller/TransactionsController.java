package lt.lukasnakas.controller;

import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/transactions")
public class TransactionsController {

	@Autowired
	private BankService bankService;

	@GetMapping(value = "", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Transaction>> getAllTransactions() {
		return new ResponseEntity<>(bankService.getTransactions(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable String id){
		return new ResponseEntity<>(bankService.getTransactionById(id), HttpStatus.OK);
	}

	@PostMapping(value = "/{bankName}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Transaction> addTransaction(@RequestBody String paymentBody, @PathVariable String bankName) {
		return new ResponseEntity<>(bankService.postTransaction(paymentBody, bankName), HttpStatus.OK);
	}

}