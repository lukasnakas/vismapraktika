package lt.lukasnakas.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lt.lukasnakas.Configuration.RevolutServiceConfiguration;
import lt.lukasnakas.Model.RevolutAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class RevolutAccountService {
    private final RevolutServiceConfiguration revolutConfig;

    @Autowired
    public RevolutAccountService(RevolutServiceConfiguration revolutConfig) {
        this.revolutConfig = revolutConfig;
    }

    public List<RevolutAccount> getAllAccounts(){
        revolutConfig.setAccessToken(refreshAccessToken());
        String authorizationHeader = revolutConfig.getTokenType() + " " + revolutConfig.getAccessToken();

        String accounts = attemptFetchingAccounts(authorizationHeader);

        Gson gsonParser = new Gson();
        Type revolutAccountListType = new TypeToken<List<RevolutAccount>>(){}.getType();
        List<RevolutAccount> revolutAccountList = gsonParser.fromJson(accounts, revolutAccountListType);

        return revolutAccountList;
    }

    private String attemptFetchingAccounts(String authorizationHeader){
        String response;

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get(revolutConfig.getUrlAccounts())
                    .header("content-type", "application/json")
                    .header("Authorization", authorizationHeader)
                    .asJson();

            if(httpResponse == null)
                return null;

            if(httpResponse.getBody().getObject() != null && httpResponse.getBody().getObject().has("message")) {
                System.out.println("You must renew access token");
                return null;
            }

            response = httpResponse.getBody().toString();
            return response;
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String refreshAccessToken(){
        String newAccessToken = "";

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.post(revolutConfig.getUrlAuth())
                    .field("grant_type", revolutConfig.getGrantType())
                    .field("client_id", revolutConfig.getClientId())
                    .field("refresh_token", revolutConfig.getRefreshToken())
                    .field("client_assertion_type", revolutConfig.getClientAssertionType())
                    .field("client_assertion", revolutConfig.getClientAssertion())
                    .asJson();

            newAccessToken = httpResponse.getBody().getObject().getString("access_token");
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        System.out.println("New access token: " + newAccessToken);
        return newAccessToken;
    }

}
