package lt.lukasnakas.service.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.danske.DanskeAccount;
import lt.lukasnakas.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DanskeAccountService implements AccountService {
    @Autowired
    private DanskeServiceConfiguration danskeServiceConfiguration;

    @Autowired
    private DanskeTokenRenewalService danskeTokenRenewalService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    public List<Account> retrieveAccounts(){
        ResponseEntity<List<DanskeAccount>> responseEntity;

        try {
            String accessToken = danskeServiceConfiguration.getAccessToken();
            responseEntity = getResponseEntity(accessToken);
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = danskeTokenRenewalService.generateAccessToken();
            responseEntity = getResponseEntity(accessToken);
        }

        return getParsedAccountList(responseEntity.getBody());
    }

    private HttpEntity getRequestEntity(String accessToken){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity(httpHeaders);
    }

    private ResponseEntity<List<DanskeAccount>> getResponseEntity(String accessToken){
        return restTemplate.exchange(
                danskeServiceConfiguration.getUrlAccounts(),
                HttpMethod.GET,
                getRequestEntity(accessToken),
                new ParameterizedTypeReference<List<DanskeAccount>>() {});
    }

    public List<Account> getParsedAccountList(List<? extends Account> unparsedAccoutsList){
        return new ArrayList<>(unparsedAccoutsList);
    }
}
