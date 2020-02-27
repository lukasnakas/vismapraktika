package lt.lukasnakas.controller;

import lt.lukasnakas.exception.TransactionNotFoundException;
import lt.lukasnakas.model.dto.CommonTransactionDTO;
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

    @GetMapping
    public ResponseEntity<List<CommonTransactionDTO>> getAllTransactions() {
        return ok(transactionService.getTransactions());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommonTransactionDTO> getTransactionById(@PathVariable String id) {
        try {
            return ok(transactionService.getTransactionById(id));
        } catch (TransactionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/update")
    public ResponseEntity<List<CommonTransactionDTO>> updateTransactions() {
        return ok(transactionService.updateTransactions());
    }
}