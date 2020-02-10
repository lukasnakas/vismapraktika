package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.model.revolut.RevolutAccessToken;
import lt.lukasnakas.service.TokenRenewalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class RevolutTokenRenewalService implements TokenRenewalService {

    private static final Logger logger = LoggerFactory.getLogger(RevolutTokenRenewalService.class);

    @Autowired
    private RevolutServiceConfiguration revolutServiceConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    public String generateAccessToken(){
        ResponseEntity<RevolutAccessToken> response = restTemplate.postForEntity(revolutServiceConfiguration.getUrlAuth(), getRequestBodyParams(), RevolutAccessToken.class);
        logger.info("New access token: {}", response.getBody().getAccessToken());
        return response.getBody().getAccessToken();
    }

    public Map<String, String> getRequestBodyParams(){
        Map<String, String> bodyParams = new HashMap<>();

        bodyParams.put("grant_type", revolutServiceConfiguration.getGrantType());
        bodyParams.put("client_id", revolutServiceConfiguration.getClientId());
        bodyParams.put("refresh_token", revolutServiceConfiguration.getRefreshToken());
        bodyParams.put("client_assertion_type", revolutServiceConfiguration.getClientAssertionType());
        bodyParams.put("client_assertion", revolutServiceConfiguration.getClientAssertion());

        return bodyParams;
    }

}
