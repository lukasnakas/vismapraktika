package lt.lukasnakas.controller;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.service.revolut.RevolutAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RevolutController {
    @Autowired
    private RevolutAccountService revolutAccountService;

    @GetMapping(value = "/banks/revolut/accounts")
    public ResponseEntity<List<Account>> getRevolutAccounts(){
        return new ResponseEntity<>(revolutAccountService.retrieveAccounts(), HttpStatus.OK);
    }
}
