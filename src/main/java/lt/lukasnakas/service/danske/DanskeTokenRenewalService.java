package lt.lukasnakas.service.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.AccessToken;
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

	public AccessToken generateAccessToken() {
		ResponseEntity<AccessToken> responseEntity;

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
			danskeServiceConfiguration.setAccessToken(newAccessToken);
			LOGGER.info("[{}] Generated new access token [{}]", danskeServiceConfiguration.getName(), newAccessToken);
		}
	}

	private ResponseEntity<AccessToken> getResponseEntityForAccessToken() {
		return restTemplate.postForEntity(
				danskeServiceConfiguration.getUrlAuth(),
				getRequestBodyParams(),
				AccessToken.class);
	}

	public MultiValueMap<String, String> getRequestBodyParams() {
		MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();

		bodyParams.add("ClientId", danskeServiceConfiguration.getClientId());
		bodyParams.add("Secret", danskeServiceConfiguration.getSecret());

		return bodyParams;
	}

}
