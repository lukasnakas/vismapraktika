package lt.lukasnakas.service;

import lt.lukasnakas.model.AccessToken;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface TokenRenewalService {
	AccessToken generateAccessToken();

	void setupNewAccessToken(ResponseEntity<? extends AccessToken> response);

	MultiValueMap<String, String> getRequestBodyParams();
}
