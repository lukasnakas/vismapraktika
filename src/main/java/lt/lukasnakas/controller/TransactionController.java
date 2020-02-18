package lt.lukasnakas.controller;

import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/transactions")
public class TransactionController {
	private final BankService bankService;

	public TransactionController(BankService bankService) {
		this.bankService = bankService;
	}

	@GetMapping(value = "", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Transaction>> getAllTransactions() {
		return ok(bankService.getTransactionList());
	}

	@GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
		try {
			return ok(bankService.getTransactionById(id));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@PostMapping(value = "/{bankName}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Transaction> addTransaction(@RequestBody Payment payment, @PathVariable String bankName) {
		try {
			return ok(bankService.postTransaction(payment, bankName));
		} catch (BadRequestException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}