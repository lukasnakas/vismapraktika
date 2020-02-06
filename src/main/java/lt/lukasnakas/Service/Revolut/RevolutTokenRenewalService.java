package lt.lukasnakas.Service.Revolut;

import lt.lukasnakas.Configuration.RevolutServiceConfiguration;
import lt.lukasnakas.Model.Revolut.RevolutAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RevolutTokenRenewalService {

    @Autowired
    private RevolutServiceConfiguration revolutConfig;

    @Autowired
    private RestTemplate restTemplate;

    public String generateAccessToken(){
        ResponseEntity<RevolutAccessToken> response = restTemplate.postForEntity(revolutConfig.getUrlAuth(), getRequestBodyParams(), RevolutAccessToken.class);
        System.out.println("New access token: " + response.getBody().getAccess_token());
        return response.getBody().getAccess_token();
    }

    private MultiValueMap<String, String> getRequestBodyParams(){
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();

        bodyParams.add("grant_type", revolutConfig.getGrantType());
        bodyParams.add("client_id", revolutConfig.getClientId());
        bodyParams.add("refresh_token", revolutConfig.getRefreshToken());
        bodyParams.add("client_assertion_type", revolutConfig.getClientAssertionType());
        bodyParams.add("client_assertion", revolutConfig.getClientAssertion());

        return bodyParams;
    }

}
