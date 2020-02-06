package lt.lukasnakas.Service.Danske;

import lt.lukasnakas.Configuration.DanskeServiceConfiguration;
import lt.lukasnakas.Model.Danske.DanskeAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class DanskeTokenRenewalService {

    @Autowired
    private DanskeServiceConfiguration danskeConfig;

    @Autowired
    private RestTemplate restTemplate;

    public String generateAccessToken(){
        ResponseEntity<DanskeAccessToken> response = restTemplate.postForEntity(danskeConfig.getUrlAuth(), getRequestBodyParams(), DanskeAccessToken.class);
        System.out.println("New access token: " + response.getBody().getAccessToken());
        return response.getBody().getAccessToken();
    }

    private MultiValueMap<String, String> getRequestBodyParams(){
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();

        bodyParams.add("ClientId", danskeConfig.getClientId());
        bodyParams.add("Secret", danskeConfig.getSecret());

        return bodyParams;
    }

}
