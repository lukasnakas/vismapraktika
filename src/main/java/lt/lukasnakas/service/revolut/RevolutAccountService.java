package lt.lukasnakas.service.revolut;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.revolut.RevolutAccount;
import lt.lukasnakas.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class RevolutAccountService implements AccountService {
    @Autowired
    private RevolutServiceConfiguration revolutServiceConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Autowired
    private RevolutTokenRenewalService revolutTokenRenewalService;

    public List<Account> retrieveAccounts() {
        ResponseEntity<String> responseEntity;

        try {
            String accessToken = revolutServiceConfiguration.getAccessToken();
            responseEntity = restTemplate.exchange(revolutServiceConfiguration.getUrlAccounts(), HttpMethod.GET, getRequestEntity(accessToken), String.class);
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = revolutTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(revolutServiceConfiguration.getUrlAccounts(), HttpMethod.GET, getRequestEntity(accessToken), String.class);
        }

        return getParsedAccounts(responseEntity.getBody());
    }

    public List<Account> getParsedAccounts(String accounts){
        Gson gson = new Gson();
        Type revolutAccountListType = new TypeToken<List<RevolutAccount>>(){}.getType();
        return gson.fromJson(accounts, revolutAccountListType);
    }

    private HttpEntity getRequestEntity(String accessToken){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity(httpHeaders);
    }

}
