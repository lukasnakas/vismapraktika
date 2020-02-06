package lt.lukasnakas.Service.Danske;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lt.lukasnakas.Configuration.DanskeServiceConfiguration;
import lt.lukasnakas.Model.Danske.DanskeAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class DanskeAccountService {
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

    public List<DanskeAccount> getAllAccounts(){
        accessToken = danskeTokenRenewalService.generateAccessToken();
        danskeConfig.setAccessToken(accessToken);

        String accounts = retrieveAccounts();

        Gson gsonParser = new Gson();
        Type danskeAccountListType = new TypeToken<List<DanskeAccount>>(){}.getType();
        List<DanskeAccount> danskeAccountList = gsonParser.fromJson(accounts, danskeAccountListType);

        return danskeAccountList;
    }

    private String retrieveAccounts(){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(danskeConfig.getAccessToken());

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(danskeConfig.getUrlAccounts(), HttpMethod.GET, requestEntity, String.class);

        return responseEntity.getBody();
    }

}
