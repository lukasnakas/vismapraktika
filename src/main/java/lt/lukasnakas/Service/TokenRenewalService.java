package lt.lukasnakas.Service;

import org.springframework.util.MultiValueMap;

public interface TokenRenewalService {
    String generateAccessToken();
    MultiValueMap<String, String> getRequestBodyParams();
}
