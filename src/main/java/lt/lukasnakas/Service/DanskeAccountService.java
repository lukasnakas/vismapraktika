package lt.lukasnakas.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lt.lukasnakas.Configuration.DanskeServiceConfiguration;
import lt.lukasnakas.Model.DanskeAccount;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class DanskeAccountService {
    private final DanskeServiceConfiguration danskeConfig;

    @Autowired
    public DanskeAccountService(DanskeServiceConfiguration danskeConfig) {
        this.danskeConfig = danskeConfig;
    }

    public List<DanskeAccount> getAllAccounts(){
        danskeConfig.setAccessToken(refreshAccessToken());
        String authorizationHeader = danskeConfig.getTokenType() + " " + danskeConfig.getAccessToken();

        String accounts = attemptFetchingAccounts(authorizationHeader);

        Gson gsonParser = new Gson();
        Type danskeAccountListType = new TypeToken<List<DanskeAccount>>(){}.getType();
        List<DanskeAccount> danskeAccountList = gsonParser.fromJson(accounts, danskeAccountListType);

        return danskeAccountList;
    }

    private String attemptFetchingAccounts(String authorizationHeader){
        String response;

        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get(danskeConfig.getUrlAccounts())
                    .header("content-type", "application/json")
                    .header("Authorization", authorizationHeader)
                    .asJson();

            if(httpResponse == null)
                return null;

            if(httpResponse.getBody().getObject() != null && httpResponse.getBody().getObject().has("message")) {
                System.out.println("You must renew access token");
                return null;
            }

            JSONObject httpReponseAsJSON = (JSONObject) httpResponse.getBody().getArray().get(0);

            if(httpReponseAsJSON.has("Account")) {
                response = httpReponseAsJSON.get("Account").toString();
                return response;
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String refreshAccessToken(){
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
