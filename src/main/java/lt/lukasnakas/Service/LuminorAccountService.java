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
        String authorizationHeader = luminorConfig.getTokenType() + " " + luminorConfig.getAccessToken();

        String accounts = attemptFetchingAccounts(authorizationHeader);

        Gson gsonParser = new Gson();
        Type luminorAccountListType = new TypeToken<List<LuminorAccount>>(){}.getType();
        List<LuminorAccount> luminorAccountList = gsonParser.fromJson(accounts, luminorAccountListType);

        if(accounts == null){
            luminorConfig.setAccessToken(refreshAccessToken());
            authorizationHeader = luminorConfig.getTokenType() + " " + luminorConfig.getAccessToken();

            accounts = attemptFetchingAccounts(authorizationHeader);
            luminorAccountListType = new TypeToken<List<LuminorAccount>>(){}.getType();
            luminorAccountList = gsonParser.fromJson(accounts, luminorAccountListType);
        }

        return luminorAccountList;
    }

    private String attemptFetchingAccounts(String authorizationHeader){
        String response;

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get(luminorConfig.getUrlAccounts())
                    .header("content-type", "application/json")
                    .header("Authorization", authorizationHeader)
                    .asJson();

            if(httpResponse == null)
                return null;

            if(httpResponse.getBody().getObject() != null && httpResponse.getBody().getObject().has("message")) {
                System.out.println("You must renew access token");
                return null;
            }

            if(httpResponse.getBody().getObject().has("accounts")) {
                response = httpResponse.getBody().getObject().get("accounts").toString();
                return response;
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String refreshAccessToken(){
        String newAccessToken;

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.post(luminorConfig.getUrlAuthAccessToken())
                    .header("content-type", "application/x-www-form-urlencoded")
                    .field("client_id",     luminorConfig.getClientId())
                    .field("client_secret", luminorConfig.getClientSecret())
                    .field("redirect_uri",  luminorConfig.getRedirectUri())
                    .field("realm",         luminorConfig.getRealm())
                    .field("grant_type",    luminorConfig.getGrantType())
                    .field("code",          luminorConfig.getAuthCode())
                    .asJson();
            System.out.println(httpResponse.getBody());

            if(httpResponse.getBody().getObject().has("access_token")) {
                newAccessToken = httpResponse.getBody().getObject().get("access_token").toString();
                System.out.println("New access token: " + newAccessToken);
                return newAccessToken;
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

}
