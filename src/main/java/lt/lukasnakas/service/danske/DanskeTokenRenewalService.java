package lt.lukasnakas.service.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.exception.TokenGenerationException;
import lt.lukasnakas.model.AccessToken;
import lt.lukasnakas.service.ITokenRenewalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class DanskeTokenRenewalService implements ITokenRenewalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DanskeTokenRenewalService.class);

    private final DanskeServiceConfiguration danskeServiceConfiguration;
    private final RestTemplate restTemplate;

    public DanskeTokenRenewalService(DanskeServiceConfiguration danskeServiceConfiguration,
                                     RestTemplate restTemplate) {
        this.danskeServiceConfiguration = danskeServiceConfiguration;
        this.restTemplate = restTemplate;
    }

    public AccessToken generateAccessToken() {
        ResponseEntity<AccessToken> responseEntity = getAccessTokenResponseEntity();
        AccessToken accessToken = extractAccessToken(responseEntity);
        danskeServiceConfiguration.setAccessToken(accessToken.getToken());
        LOGGER.info("[{}] Generated new access token [{}]", danskeServiceConfiguration.getName(), accessToken.getToken());
        return responseEntity.getBody();
    }

    public AccessToken extractAccessToken(ResponseEntity<? extends AccessToken> responseEntity) {
        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new TokenGenerationException("Failed to generate new access token"));
    }

    public ResponseEntity<AccessToken> getAccessTokenResponseEntity() {
        try {
            return restTemplate.postForEntity(
                    danskeServiceConfiguration.getUrlAuth(),
                    getRequestBodyParams(),
                    AccessToken.class);
        } catch (Exception e) {
            throw new TokenGenerationException(e.getMessage());
        }
    }

    private MultiValueMap<String, String> getRequestBodyParams() {
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();

        bodyParams.add("ClientId", danskeServiceConfiguration.getClientId());
        bodyParams.add("Secret", danskeServiceConfiguration.getSecret());

        return bodyParams;
    }

}