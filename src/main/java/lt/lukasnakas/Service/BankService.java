package lt.lukasnakas.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lt.lukasnakas.Configuration.LuminorServiceConfiguration;
import lt.lukasnakas.Configuration.RevolutServiceConfiguration;
import lt.lukasnakas.Model.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private List<Bank> bankList;

    private final RevolutServiceConfiguration revolutConfig;
    private final LuminorServiceConfiguration luminorConfig;

    @Autowired
    public BankService(List<Bank> bankList, RevolutServiceConfiguration revolutConfig, LuminorServiceConfiguration luminorConfig) {
        this.bankList = bankList;
        this.revolutConfig = revolutConfig;
        this.luminorConfig = luminorConfig;

        this.bankList.add(new Bank(1, this.revolutConfig.getName(), this.revolutConfig.getAccessToken(), this.revolutConfig.getUrlAccounts()));
        this.bankList.add(new Bank(2, luminorConfig.getName(), luminorConfig.getAccessToken(), luminorConfig.getUrlAccounts()));
    }

    public List<Bank> getAllBanks(){
        return bankList;
    }

    public Bank getBankByID(int id){
        return bankList.get(id - 1);
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
