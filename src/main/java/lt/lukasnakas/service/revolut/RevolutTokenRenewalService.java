package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.exception.TokenGenerationException;
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

import java.util.Optional;

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
        ResponseEntity<RevolutAccessToken> responseEntity = getRevolutAccessTokenResponseEntity();
        AccessToken accessToken = extractAccessToken(responseEntity);
        revolutServiceConfiguration.setAccessToken(accessToken.getToken());
        LOGGER.info("[{}] Generated new access token [{}]", revolutServiceConfiguration.getName(), accessToken.getToken());
        return responseEntity.getBody();
    }

    public AccessToken extractAccessToken(ResponseEntity<? extends AccessToken> responseEntity) {
        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new TokenGenerationException("Failed to generate new access token"));
    }

    public ResponseEntity<RevolutAccessToken> getRevolutAccessTokenResponseEntity() {
        try {
            return restTemplate.postForEntity(
                    revolutServiceConfiguration.getUrlAuth(),
                    getRequestBodyParams(),
                    RevolutAccessToken.class);
        } catch (Exception e) {
            throw new TokenGenerationException(e.getMessage());
        }
    }

    private MultiValueMap<String, String> getRequestBodyParams() {
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();

        bodyParams.add("grant_type", revolutServiceConfiguration.getGrantType());
        bodyParams.add("client_id", revolutServiceConfiguration.getClientId());
        bodyParams.add("refresh_token", revolutServiceConfiguration.getRefreshToken());
        bodyParams.add("client_assertion_type", revolutServiceConfiguration.getClientAssertionType());
        bodyParams.add("client_assertion", revolutServiceConfiguration.getClientAssertion());

        return bodyParams;
    }

}