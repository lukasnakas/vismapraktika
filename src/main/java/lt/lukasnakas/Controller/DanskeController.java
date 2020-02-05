package lt.lukasnakas.Controller;

import lt.lukasnakas.Model.DanskeAccount;
import lt.lukasnakas.Service.DanskeAccountService;
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
    public ResponseEntity<List<DanskeAccount>> getDanskeAccounts(){
        return new ResponseEntity<>(danskeAccountService.getAllAccounts(), HttpStatus.OK);
    }
}
