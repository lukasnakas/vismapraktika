package lt.lukasnakas.Controller;

import lt.lukasnakas.Model.LuminorAccount;
import lt.lukasnakas.Service.LuminorAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LuminorController {
    @Autowired
    private LuminorAccountService luminorAccountService;

    @GetMapping(value = "/banks/luminor/accounts")
    public ResponseEntity<List<LuminorAccount>> getLuminorAccounts(){
        return new ResponseEntity<>(luminorAccountService.getAllAccounts(), HttpStatus.OK);
    }
}
