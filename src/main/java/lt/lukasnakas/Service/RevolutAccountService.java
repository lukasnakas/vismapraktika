package lt.lukasnakas.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lt.lukasnakas.Configuration.RevolutServiceConfiguration;
import lt.lukasnakas.Model.RevolutAccount;
import org.json.JSONObject;
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
        String newAccessToken = "";

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.post(revolutConfig.getUrlAuth())
                    .field("grant_type",            revolutConfig.getGrantType())
                    .field("client_id",             revolutConfig.getClientId())
                    .field("refresh_token",         revolutConfig.getRefreshToken())
                    .field("client_assertion_type", revolutConfig.getClientAssertionType())
                    .field("client_assertion",      revolutConfig.getClientAssertion())
                    .asJson();

            newAccessToken = httpResponse.getBody().getObject().getString("access_token");
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        System.out.println("New access token: " + newAccessToken);
        return newAccessToken;
    }

}
