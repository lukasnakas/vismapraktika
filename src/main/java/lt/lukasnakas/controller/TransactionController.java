package lt.lukasnakas.controller;

import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/transactions")
public class TransactionController {
	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping(value = "", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<CommonTransaction>> getAllTransactions() {
		return ok(transactionService.getTransactions());
	}

	@GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<CommonTransaction> getTransactionById(@PathVariable String id) {
		try {
			return ok(transactionService.getTransactionById(id));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@PostMapping(value = "/{bankName}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<CommonTransaction> addTransaction(@RequestBody Payment payment, @PathVariable String bankName) {
		try {
			return ok(transactionService.postTransaction(payment, bankName));
		} catch (BadRequestException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<CommonTransaction>> updateAccounts(){
		return ok(transactionService.updateTransactions());
	}
}