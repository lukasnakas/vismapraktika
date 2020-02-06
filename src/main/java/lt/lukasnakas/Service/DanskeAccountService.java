package lt.lukasnakas.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import lt.lukasnakas.Configuration.DanskeServiceConfiguration;
import lt.lukasnakas.Model.Danske.DanskeAccessToken;
import lt.lukasnakas.Model.Revolut.RevolutAccessToken;
import lt.lukasnakas.Model.Danske.DanskeAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public DanskeAccountService(DanskeServiceConfiguration danskeConfig) {
        this.danskeConfig = danskeConfig;
    }

    public List<DanskeAccount> getAllAccounts(){
        danskeConfig.setAccessToken(refreshAccessToken());

        String accounts = retrieveAccounts();

        Gson gsonParser = new Gson();
        Type danskeAccountListType = new TypeToken<List<DanskeAccount>>(){}.getType();
        List<DanskeAccount> danskeAccountList = gsonParser.fromJson(accounts, danskeAccountListType);

        return danskeAccountList;
    }

    private String retrieveAccounts(){
        try {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBearerAuth(danskeConfig.getAccessToken());

            HttpEntity requestEntity = new HttpEntity(httpHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(danskeConfig.getUrlAccounts(), HttpMethod.GET, requestEntity, String.class);

            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String refreshAccessToken(){
         try {
            MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
            bodyParams.add("ClientId", danskeConfig.getClientId());
            bodyParams.add("Secret", danskeConfig.getSecret());

            ResponseEntity<DanskeAccessToken> response = restTemplate.postForEntity(danskeConfig.getUrlAuth(), bodyParams, DanskeAccessToken.class);

            System.out.println("New access token: " + response.getBody().getAccessToken());
            return response.getBody().getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
