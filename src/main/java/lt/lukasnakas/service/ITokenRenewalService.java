package lt.lukasnakas.service;

import lt.lukasnakas.model.AccessToken;
import org.springframework.http.ResponseEntity;

public interface ITokenRenewalService {
	AccessToken generateAccessToken();

	AccessToken extractAccessToken(ResponseEntity<? extends AccessToken> responseEntity);
}
