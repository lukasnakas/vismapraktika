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

    @Autowired
    private Gson gson;

    private String accessToken;

    @Autowired
    public DanskeAccountService(DanskeServiceConfiguration danskeConfig) {
        this.danskeConfig = danskeConfig;
    }

    public List<Account> getAllAccounts(){
        accessToken = danskeTokenRenewalService.generateAccessToken();
        return getParsedAccounts(retrieveAccounts());
    }

    public String retrieveAccounts(){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(danskeConfig.getUrlAccounts(), HttpMethod.GET, requestEntity, String.class);

        return responseEntity.getBody();
    }

    public List<Account> getParsedAccounts(String accounts){
        Type danskeAccountListType = new TypeToken<List<DanskeAccount>>(){}.getType();
        List<Account> danskeAccountList = gson.fromJson(accounts, danskeAccountListType);
        return danskeAccountList;
    }

}