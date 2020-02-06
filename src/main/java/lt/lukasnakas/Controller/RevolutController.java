package lt.lukasnakas.Controller;

import lt.lukasnakas.Model.Revolut.RevolutAccount;
import lt.lukasnakas.Service.RevolutAccountService;
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
    public ResponseEntity<List<RevolutAccount>> getRevolutAccounts(){
        return new ResponseEntity<>(revolutAccountService.getAllAccounts(), HttpStatus.OK);
    }
}
