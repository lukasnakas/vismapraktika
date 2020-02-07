package lt.lukasnakas.controller;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.service.danske.DanskeAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DanskeController {
    @Autowired
    private DanskeAccountService danskeAccountService;

    @GetMapping(value = "/banks/danske/accounts")
    public ResponseEntity<List<? extends Account>> getDanskeAccounts(){
        return new ResponseEntity<>(danskeAccountService.retrieveAccounts(), HttpStatus.OK);
    }
}
