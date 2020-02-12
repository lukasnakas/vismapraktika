package lt.lukasnakas.service;

import org.springframework.util.MultiValueMap;

public interface TokenRenewalService {
	String generateAccessToken();

	MultiValueMap<String, String> getRequestBodyParams();
}
