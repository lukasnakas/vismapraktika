package lt.lukasnakas.service;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface TokenRenewalService {
    String generateAccessToken();
    MultiValueMap<String, String> getRequestBodyParams();
}
