package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.model.AccessToken;
import lt.lukasnakas.model.revolut.RevolutAccessToken;
import lt.lukasnakas.service.TokenRenewalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RevolutTokenRenewalService implements TokenRenewalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RevolutTokenRenewalService.class);

    private final RevolutServiceConfiguration revolutServiceConfiguration;
    private final RestTemplate restTemplate;

    public RevolutTokenRenewalService(RevolutServiceConfiguration revolutServiceConfiguration,
                                      RestTemplate restTemplate) {
        this.revolutServiceConfiguration = revolutServiceConfiguration;
        this.restTemplate = restTemplate;
    }

    public AccessToken generateAccessToken() {
        ResponseEntity<RevolutAccessToken> responseEntity;

        try {
            responseEntity = getResponseEntityForAccessToken();
            setupNewAccessToken(responseEntity);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }

        return responseEntity.getBody();
    }

    public void setupNewAccessToken(ResponseEntity<? extends AccessToken> response) {
        if (response.getBody() != null) {
            String newAccessToken = response.getBody().getToken();
            revolutServiceConfiguration.setAccessToken(newAccessToken);
            LOGGER.info("[{}] Generated new access token [{}]", revolutServiceConfiguration.getName(), newAccessToken);
        }
    }

    private ResponseEntity<RevolutAccessToken> getResponseEntityForAccessToken() {
        return restTemplate.postForEntity(
                revolutServiceConfiguration.getUrlAuth(),
                getRequestBodyParams(),
                RevolutAccessToken.class);
    }

    public MultiValueMap<String, String> getRequestBodyParams() {
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();

        bodyParams.add("grant_type", revolutServiceConfiguration.getGrantType());
        bodyParams.add("client_id", revolutServiceConfiguration.getClientId());
        bodyParams.add("refresh_token", revolutServiceConfiguration.getRefreshToken());
        bodyParams.add("client_assertion_type", revolutServiceConfiguration.getClientAssertionType());
        bodyParams.add("client_assertion", revolutServiceConfiguration.getClientAssertion());

        return bodyParams;
    }

}
