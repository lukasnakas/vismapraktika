package lt.lukasnakas.Service.Danske;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lt.lukasnakas.Configuration.DanskeServiceConfiguration;
import lt.lukasnakas.Model.Account;
import lt.lukasnakas.Model.Danske.DanskeAccount;
import lt.lukasnakas.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class DanskeAccountService implements AccountService {
    private final DanskeServiceConfiguration danskeConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Autowired
    private DanskeTokenRenewalService danskeTokenRenewalService;

    private String accessToken;

    @Autowired
    public DanskeAccountService(DanskeServiceConfiguration danskeConfig) {
        this.danskeConfig = danskeConfig;
    }

    public List<Account> retrieveAccounts(){
        ResponseEntity<String> responseEntity;

        try {
            accessToken = danskeConfig.getAccessToken();
            responseEntity = restTemplate.exchange(danskeConfig.getUrlAccounts(), HttpMethod.GET, getRequestEntity(accessToken), String.class);
        } catch (Exception e){
            accessToken = danskeTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(danskeConfig.getUrlAccounts(), HttpMethod.GET, getRequestEntity(accessToken), String.class);
        }

        return getParsedAccounts(responseEntity.getBody());
    }

    public List<Account> getParsedAccounts(String accounts){
        Gson gson = new Gson();
        Type danskeAccountListType = new TypeToken<List<DanskeAccount>>(){}.getType();
        List<Account> danskeAccountList = gson.fromJson(accounts, danskeAccountListType);
        return danskeAccountList;
    }

    private HttpEntity getRequestEntity(String accessToken){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        return requestEntity;
    }
}
