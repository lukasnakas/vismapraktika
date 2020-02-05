package lt.lukasnakas.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lt.lukasnakas.Configuration.LuminorServiceConfiguration;
import lt.lukasnakas.Model.LuminorAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class LuminorAccountService {

    private final LuminorServiceConfiguration luminorConfig;

    @Autowired
    public LuminorAccountService(LuminorServiceConfiguration luminorConfig) {
        this.luminorConfig = luminorConfig;
    }

    public List<LuminorAccount> getAllAccounts(){
        String tokenType = "Bearer";
        String authorizationHeader = tokenType + " " + luminorConfig.getAccessToken();

        String accounts = attemptFetchingAccounts(authorizationHeader);

        Gson gsonParser = new Gson();
        Type luminorAccountListType = new TypeToken<List<LuminorAccount>>(){}.getType();
        List<LuminorAccount> luminorAccountList = gsonParser.fromJson(accounts, luminorAccountListType);

        if(accounts == null){
            luminorConfig.setAccessToken(refreshAccessToken());
            authorizationHeader = tokenType + " " + luminorConfig.getAccessToken();

            accounts = attemptFetchingAccounts(authorizationHeader);
            luminorAccountListType = new TypeToken<List<LuminorAccount>>(){}.getType();
            luminorAccountList = gsonParser.fromJson(accounts, luminorAccountListType);
        }

        return luminorAccountList;
    }

    private String attemptFetchingAccounts(String authorizationHeader){
        String response;

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get(luminorConfig.getUrlAccounts()).header("Authorization", authorizationHeader).asJson();

            if(httpResponse == null)
                return null;

            if(httpResponse.getBody().getObject() != null && httpResponse.getBody().getObject().has("message")) {
                System.out.println("You must renew access token");
                return null;
            }

            response = httpResponse.getBody().getObject().get("accounts").toString();
            return response;
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String refreshAccessToken(){
        String newAccessToken = "";

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.post(luminorConfig.getUrlAuth())
                    .field("grant_type", luminorConfig.getGrantType())
                    .field("client_id", luminorConfig.getClientId())
                    .field("refresh_token", luminorConfig.getRefreshToken())
                    .field("client_assertion_type", luminorConfig.getClientAssertionType())
                    .field("client_assertion", luminorConfig.getClientAssertion())
                    .asJson();

            newAccessToken = httpResponse.getBody().getObject().getString("access_token");
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        System.out.println("New access token: " + newAccessToken);
        return newAccessToken;
    }
}
