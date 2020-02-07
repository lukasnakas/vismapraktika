package lt.lukasnakas.service.danske;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.danske.DanskeAccount;
import lt.lukasnakas.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class DanskeAccountService implements AccountService {
    @Autowired
    private DanskeServiceConfiguration danskeServiceConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Autowired
    private DanskeTokenRenewalService danskeTokenRenewalService;

    public List<Account> retrieveAccounts(){
        ResponseEntity<String> responseEntity;

        try {
            String accessToken = danskeServiceConfiguration.getAccessToken();
            responseEntity = restTemplate.exchange(danskeServiceConfiguration.getUrlAccounts(), HttpMethod.GET, getRequestEntity(accessToken), String.class);
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = danskeTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(danskeServiceConfiguration.getUrlAccounts(), HttpMethod.GET, getRequestEntity(accessToken), String.class);
        }

        return getParsedAccounts(responseEntity.getBody());
    }

    public List<Account> getParsedAccounts(String accounts){
        Gson gson = new Gson();
        Type danskeAccountListType = new TypeToken<List<DanskeAccount>>(){}.getType();
        return gson.fromJson(accounts, danskeAccountListType);
    }

    private HttpEntity getRequestEntity(String accessToken){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity(httpHeaders);
    }
}
