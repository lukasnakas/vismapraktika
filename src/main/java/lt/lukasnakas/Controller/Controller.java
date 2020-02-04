package lt.lukasnakas.Controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lt.lukasnakas.Model.Bank;
import lt.lukasnakas.Service.BankService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
            return "";

        String tokenType = "Bearer";
        String authorizationHeader = tokenType + " " + bank.getAccessToken();
        String accounts = "";

        accounts = attemptFetchingAccounts(bank, authorizationHeader);

//        if(accounts == null){
//            bank.setAccessToken(refreshAccessToken());
//            accounts = attemptFetchingAccounts(bank, authorizationHeader);
//        }

        return accounts;
    }

    private String attemptFetchingAccounts(Bank bank, String authorizationHeader){
        String response = "";

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get(bank.getURL()).header("Authorization", authorizationHeader).asJson();

            if(httpResponse == null)
                return null;

            if(httpResponse.getBody().getObject() != null && httpResponse.getBody().getObject().has("message"))
                return "You must renew access token";

            response = httpResponse.getBody().toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String refreshAccessToken(){
        String newAccessToken = "";

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.post("https://sandbox-b2b.revolut.com/api/1.0/auth/token")
                    .field("grant_type", "refresh_token")
                    .field("client_id", "lLtj8AIjzoaPjgwW4OpDqJfVrMUoVDjGNY4XNMIETXI")
                    .field("refresh_token", "oa_sand_uy7EROH9X5Gw6W_B3v9HSIS7SVtDFcOVYA2xeNN0QEQ")
                    .field("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
                    .field("client_assertion", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzYW5kYm94LWIyYi5yZXZvbHV0LmNvbSIsInN1YiI6ImxMdGo4QUlqem9hUGpnd1c0T3BEcUpmVnJNVW9WRGpHTlk0WE5NSUVUWEkiLCJhdWQiOiJodHRwczovL3Jldm9sdXQuY29tIiwiZXhwIjoxODkzNDU2MDAwfQ.G5MaRQJ6feATXLwrJIsRuKtguINOtkLJznUueN0iBXU74g3goTm6mnly_BgJsO_lDH4AuunDOCw_LFcWGvXTOkwKdgrkif3j83aZXWF6W0HjNTnNSX_fLq4268xH9GK6eJI0YE42eY1wikNDJEGePqqydkTdmX6t0_K5nEglcCY")
                    .asJson();

            newAccessToken = httpResponse.getBody().getObject().getString("access_token");
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        System.out.println("New access token: " + newAccessToken);
        return newAccessToken;
    }

}
