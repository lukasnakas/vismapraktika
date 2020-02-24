package lt.lukasnakas.controller;

import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.dto.CommonAccountDTO;
import lt.lukasnakas.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CommonAccountDTO>> getAllAccounts() {
        return ok(accountService.getAccounts());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommonAccountDTO> getAccountById(@PathVariable String id) {
        try {
            return ok(accountService.getAccountById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping(value = "/update")
    public ResponseEntity<List<CommonAccountDTO>> updateAccounts() {
        return ok(accountService.updateAccounts());
    }

}
