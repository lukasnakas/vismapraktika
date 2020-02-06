package lt.lukasnakas.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lt.lukasnakas.Configuration.RevolutServiceConfiguration;
import lt.lukasnakas.Model.Revolut.RevolutAccessToken;
import lt.lukasnakas.Model.Revolut.RevolutAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public RevolutAccountService(RevolutServiceConfiguration revolutConfig) {
        this.revolutConfig = revolutConfig;
    }

    public List<RevolutAccount> getAllAccounts(){
        revolutConfig.setAccessToken(refreshAccessToken());

        String accounts = retrieveAccounts();

        Gson gsonParser = new Gson();
        Type revolutAccountListType = new TypeToken<List<RevolutAccount>>(){}.getType();
        List<RevolutAccount> revolutAccountList = gsonParser.fromJson(accounts, revolutAccountListType);

        return revolutAccountList;
    }

    private String retrieveAccounts(){
        try {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBearerAuth(revolutConfig.getAccessToken());

            HttpEntity requestEntity = new HttpEntity(httpHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(revolutConfig.getUrlAccounts(), HttpMethod.GET, requestEntity, String.class);

            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String refreshAccessToken(){
        try {
            MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
            bodyParams.add("grant_type", revolutConfig.getGrantType());
            bodyParams.add("client_id", revolutConfig.getClientId());
            bodyParams.add("refresh_token", revolutConfig.getRefreshToken());
            bodyParams.add("client_assertion_type", revolutConfig.getClientAssertionType());
            bodyParams.add("client_assertion", revolutConfig.getClientAssertion());

            ResponseEntity<RevolutAccessToken> response = restTemplate.postForEntity(revolutConfig.getUrlAuth(), bodyParams, RevolutAccessToken.class);

            System.out.println("New access token: " + response.getBody().getAccess_token());
            return response.getBody().getAccess_token();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
