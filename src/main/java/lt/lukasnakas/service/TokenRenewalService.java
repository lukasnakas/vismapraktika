package lt.lukasnakas.service;

import java.util.Map;

public interface TokenRenewalService {
    String generateAccessToken();
    Map<String, String> getRequestBodyParams();
}
