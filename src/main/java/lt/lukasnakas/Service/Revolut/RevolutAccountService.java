package lt.lukasnakas.Service.Revolut;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lt.lukasnakas.Configuration.RevolutServiceConfiguration;
import lt.lukasnakas.Model.Revolut.RevolutAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class RevolutAccountService {
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

    public List<RevolutAccount> getAllAccounts(){
        accessToken = revolutTokenRenewalService.generateAccessToken();
        revolutConfig.setAccessToken(accessToken);

        String accounts = retrieveAccounts();

        Gson gsonParser = new Gson();
        Type revolutAccountListType = new TypeToken<List<RevolutAccount>>(){}.getType();
        List<RevolutAccount> revolutAccountList = gsonParser.fromJson(accounts, revolutAccountListType);

        return revolutAccountList;
    }

    private String retrieveAccounts(){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(revolutConfig.getAccessToken());

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(revolutConfig.getUrlAccounts(), HttpMethod.GET, requestEntity, String.class);

        return responseEntity.getBody();
    }

}
