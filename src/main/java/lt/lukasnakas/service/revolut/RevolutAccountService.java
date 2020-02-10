package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.revolut.RevolutAccount;
import lt.lukasnakas.service.AccountService;
import lt.lukasnakas.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RevolutAccountService implements AccountService {
    @Autowired
    private RevolutServiceConfiguration revolutServiceConfiguration;

    @Autowired
    private RevolutTokenRenewalService revolutTokenRenewalService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    public List<Account> retrieveAccounts() {
        ResponseEntity<List<RevolutAccount>> responseEntity;

        try {
            String accessToken = revolutServiceConfiguration.getAccessToken();
            responseEntity = getResponseEntity(accessToken);
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = revolutTokenRenewalService.generateAccessToken();
            responseEntity = getResponseEntity(accessToken);
        }

        return getParsedAccountList(responseEntity.getBody());
    }

    private HttpEntity getRequestEntity(String accessToken) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity(httpHeaders);
    }

    private ResponseEntity<List<RevolutAccount>> getResponseEntity(String accessToken){
        return restTemplate.exchange(
                revolutServiceConfiguration.getUrlAccounts(),
                HttpMethod.GET,
                getRequestEntity(accessToken),
                new ParameterizedTypeReference<List<RevolutAccount>>() {});
    }

    public List<Account> getParsedAccountList(List<? extends Account> unparsedAccountsList){
        return new ArrayList<>(unparsedAccountsList);
    }
}
