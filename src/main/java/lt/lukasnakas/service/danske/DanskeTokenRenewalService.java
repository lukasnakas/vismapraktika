package lt.lukasnakas.service.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.danske.DanskeAccessToken;
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
public class DanskeTokenRenewalService implements TokenRenewalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DanskeTokenRenewalService.class);

    @Autowired
    private DanskeServiceConfiguration danskeServiceConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    public String generateAccessToken(){
        String newAccessToken = null;

        try {
            ResponseEntity<DanskeAccessToken> response = restTemplate.postForEntity(
                    danskeServiceConfiguration.getUrlAuth(),
                    getRequestBodyParams(),
                    DanskeAccessToken.class);

            if(response.getBody() != null) {
                newAccessToken = response.getBody().getAccessToken();
                danskeServiceConfiguration.setAccessToken(newAccessToken);
                LOGGER.info("{} new access token: {}", danskeServiceConfiguration.getName(), newAccessToken);
            }
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }

        return newAccessToken;
    }

    public MultiValueMap<String, String> getRequestBodyParams(){
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();

        bodyParams.add("ClientId", danskeServiceConfiguration.getClientId());
        bodyParams.add("Secret", danskeServiceConfiguration.getSecret());

        return bodyParams;
    }

}
