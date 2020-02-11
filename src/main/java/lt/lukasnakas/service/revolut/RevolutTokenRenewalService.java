package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.model.revolut.RevolutAccessToken;
import lt.lukasnakas.service.TokenRenewalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RevolutTokenRenewalService implements TokenRenewalService {
    private static final Logger logger = LoggerFactory.getLogger(RevolutTokenRenewalService.class);

    @Autowired
    private RevolutServiceConfiguration revolutServiceConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    public String generateAccessToken(){
        ResponseEntity<RevolutAccessToken> response = restTemplate.postForEntity(
                revolutServiceConfiguration.getUrlAuth(),
                getRequestBodyParams(),
                RevolutAccessToken.class);
        String newAccessToken = response.getBody().getAccessToken();
        revolutServiceConfiguration.setAccessToken(newAccessToken);
        logger.info("New access token: {}", newAccessToken);
        return newAccessToken;
    }

    public MultiValueMap<String, String> getRequestBodyParams(){
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();

        bodyParams.add("grant_type", revolutServiceConfiguration.getGrantType());
        bodyParams.add("client_id", revolutServiceConfiguration.getClientId());
        bodyParams.add("refresh_token", revolutServiceConfiguration.getRefreshToken());
        bodyParams.add("client_assertion_type", revolutServiceConfiguration.getClientAssertionType());
        bodyParams.add("client_assertion", revolutServiceConfiguration.getClientAssertion());

        return bodyParams;
    }

}
