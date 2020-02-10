package lt.lukasnakas.controller;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.service.AccountService;
import lt.lukasnakas.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {

	@Autowired
	private BankService bankService;

	@GetMapping(value = "/{bankName}/accounts")
	public ResponseEntity<List<Account>> getAccounts(@PathVariable String bankName){
		AccountService accountService = bankService.getAccountService(bankName);
		if(accountService != null)
			return new ResponseEntity<>(accountService.retrieveAccounts(), HttpStatus.OK);
		return null;
	}

}
