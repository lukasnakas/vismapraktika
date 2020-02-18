package lt.lukasnakas.controller;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

    private final BankService bankService;

    public AccountController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Account>> getAllAccounts() {
        return ok(bankService.getAccountList());
    }

    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> getAccountById(@PathVariable String id) {
        return ok(bankService.getAccountById(id));
    }

}
