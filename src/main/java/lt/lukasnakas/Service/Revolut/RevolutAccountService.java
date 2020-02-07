package lt.lukasnakas.Service.Revolut;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lt.lukasnakas.Configuration.RevolutServiceConfiguration;
import lt.lukasnakas.Model.Account;
import lt.lukasnakas.Model.Revolut.RevolutAccount;
import lt.lukasnakas.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class RevolutAccountService implements AccountService {
    private final RevolutServiceConfiguration revolutConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Autowired
    private RevolutTokenRenewalService revolutTokenRenewalService;

    private String accessToken;

    @Autowired
    public RevolutAccountService(RevolutServiceConfiguration revolutConfig) {
        this.revolutConfig = revolutConfig;
    }

    public List<Account> retrieveAccounts() {
        ResponseEntity<String> responseEntity;

        try {
            accessToken = revolutConfig.getAccessToken();
            responseEntity = restTemplate.exchange(revolutConfig.getUrlAccounts(), HttpMethod.GET, getRequestEntity(accessToken), String.class);
        } catch (Exception e){
            accessToken = revolutTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(revolutConfig.getUrlAccounts(), HttpMethod.GET, getRequestEntity(accessToken), String.class);
        }

        return getParsedAccounts(responseEntity.getBody());
    }

    public List<Account> getParsedAccounts(String accounts){
        Gson gson = new Gson();
        Type revolutAccountListType = new TypeToken<List<RevolutAccount>>(){}.getType();
        List<Account> revolutAccountList = gson.fromJson(accounts, revolutAccountListType);
        return revolutAccountList;
    }

    private HttpEntity getRequestEntity(String accessToken){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        return requestEntity;
    }

}
