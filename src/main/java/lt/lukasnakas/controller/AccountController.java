package lt.lukasnakas.controller;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping(value = "", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Account>> getAllAccounts() {
		return ok(accountService.getAccountMap());
	}

	@GetMapping(value = "/v2", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<Account>> getAllAccountsV2() {
		return ok(accountService.getAccounts());
	}

	@GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Account> getAccountById(@PathVariable String id) {
		try {
			return ok(accountService.getAccountById(id));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@GetMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<Account>> updateAccounts(){
		return ok(accountService.updateAccounts());
	}

}
