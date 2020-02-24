package lt.lukasnakas.controller;

import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.dto.CommonTransactionDTO;
import lt.lukasnakas.model.dto.PaymentDTO;
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

    @GetMapping(value = "")
    public ResponseEntity<List<CommonTransactionDTO>> getAllTransactions() {
        return ok(transactionService.getTransactions());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommonTransactionDTO> getTransactionById(@PathVariable String id) {
        try {
            return ok(transactionService.getTransactionById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping(value = "/{bankName}")
    public ResponseEntity<CommonTransactionDTO> addTransaction(@RequestBody PaymentDTO paymentDTO, @PathVariable String bankName) {
        try {
            return ok(transactionService.postTransaction(paymentDTO, bankName));
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/update")
    public ResponseEntity<List<CommonTransactionDTO>> updateAccounts() {
        return ok(transactionService.updateTransactions());
    }
}