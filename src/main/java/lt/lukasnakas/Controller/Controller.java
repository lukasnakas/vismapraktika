package lt.lukasnakas.Controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lt.lukasnakas.Model.Bank;
import lt.lukasnakas.Service.BankService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private BankService bankService;

    @RequestMapping("/")
    public String Greet(){
        return "Hello!";
    }

    @RequestMapping("/banks")
    public ResponseEntity<List<Bank>> getBanks(){
        return new ResponseEntity<>(bankService.getAllBanks(), HttpStatus.OK);
    }

    @RequestMapping("/banks/{id}")
    public ResponseEntity<Bank> getBankByID(@PathVariable String id){
        return new ResponseEntity<>(bankService.getBankByID(Integer.parseInt(id)), HttpStatus.OK);
    }

    @RequestMapping("/banks/{id}/accounts")
    public String getAccounts(@PathVariable String id){
        Bank bank = bankService.getBankByID(Integer.parseInt(id));

        if(bank == null || bank.getAccessToken().equals(""))
            return null;

        String tokenType = "Bearer";
        String authorizationHeader = tokenType + " " + bank.getAccessToken();
        String accounts = "";

        accounts = attemptFetchingAccounts(bank, authorizationHeader);

        if(accounts == null){
            bank.setAccessToken(bankService.refreshAccessToken());
            accounts = attemptFetchingAccounts(bank, authorizationHeader);
        }

        return accounts;
    }

    private String attemptFetchingAccounts(Bank bank, String authorizationHeader){
        String response = "";

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get(bank.getURL()).header("Authorization", authorizationHeader).asJson();

            if(httpResponse == null)
                return null;

            if(httpResponse.getBody().getObject() != null && httpResponse.getBody().getObject().has("message")) {
                System.out.println("You must renew access token");
                return null;
            }

            response = httpResponse.getBody().toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return response;
    }

}
