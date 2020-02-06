package lt.lukasnakas.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lt.lukasnakas.Configuration.DanskeServiceConfiguration;
import lt.lukasnakas.Model.DanskeAccount;
import lt.lukasnakas.Model.DanskeAccountDetails;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
        String newAccessToken = "";

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.post(danskeConfig.getUrlAuth())
                    .field("ClientId", danskeConfig.getClientId())
                    .field("Secret", danskeConfig.getSecret())
                    .asJson();

            newAccessToken = httpResponse.getBody().getObject().getString("accessToken");
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        System.out.println("New access token: " + newAccessToken);
        return newAccessToken;
    }

}
