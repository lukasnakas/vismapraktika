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
	private static final Logger LOGGER = LoggerFactory.getLogger(RevolutTokenRenewalService.class);

	@Autowired
	private RevolutServiceConfiguration revolutServiceConfiguration;

	@Autowired
	private RestTemplate restTemplate;

	public String generateAccessToken() {
		String newAccessToken = null;

		try {
			ResponseEntity<RevolutAccessToken> response = restTemplate.postForEntity(
					revolutServiceConfiguration.getUrlAuth(),
					getRequestBodyParams(),
					RevolutAccessToken.class);

			if (response.getBody() != null) {
				newAccessToken = response.getBody().getAccessToken();
				revolutServiceConfiguration.setAccessToken(newAccessToken);
				LOGGER.info("{} new access token: {}", revolutServiceConfiguration.getName(), newAccessToken);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}

		return newAccessToken;
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
