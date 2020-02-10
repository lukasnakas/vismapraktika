package lt.lukasnakas.service.danske;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.danske.DanskeAccount;
import lt.lukasnakas.model.danske.DanskeTransaction;
import lt.lukasnakas.service.AccountService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DanskeService implements AccountService {
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
            responseEntity = restTemplate.exchange(
                    danskeServiceConfiguration.getUrlAccounts(),
                    HttpMethod.GET,
                    getRequestEntity(accessToken),
                    new ParameterizedTypeReference<List<DanskeAccount>>() {});
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = danskeTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(
                    danskeServiceConfiguration.getUrlAccounts(),
                    HttpMethod.GET,
                    getRequestEntity(accessToken),
                    new ParameterizedTypeReference<List<DanskeAccount>>() {});
        }

        return getParsedAccountList(responseEntity.getBody());
    }

    public List<DanskeTransaction> retrieveTransactions(){
        ResponseEntity<List<DanskeTransaction>> responseEntity;

        try {
            String accessToken = danskeServiceConfiguration.getAccessToken();
            responseEntity = restTemplate.exchange(
                    danskeServiceConfiguration.getUrlAccountTransactions(),
                    HttpMethod.GET,
                    getRequestEntity(accessToken),
                    new ParameterizedTypeReference<List<DanskeTransaction>>() {});
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = danskeTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(
                    danskeServiceConfiguration.getUrlAccountTransactions(),
                    HttpMethod.GET,
                    getRequestEntity(accessToken),
                    new ParameterizedTypeReference<List<DanskeTransaction>>() {});
        }

        return responseEntity.getBody();
    }

    public DanskeTransaction postTransaction(DanskeTransaction danskeTransaction){
        ResponseEntity<DanskeTransaction> responseEntity;

        try {
            String accessToken = danskeServiceConfiguration.getAccessToken();
            responseEntity = restTemplate.exchange(
                    danskeServiceConfiguration.getUrlAccountTransactions(),
                    HttpMethod.POST,
                    getRequestEntityWithBodyParams(accessToken, danskeTransaction),
                    DanskeTransaction.class);
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = danskeTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(
                    danskeServiceConfiguration.getUrlAccountTransactions(),
                    HttpMethod.POST,
                    getRequestEntityWithBodyParams(accessToken, danskeTransaction),
                    DanskeTransaction.class);
        }

        return responseEntity.getBody();
    }

    private HttpEntity getRequestEntity(String accessToken){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity(httpHeaders);
    }

    public HttpEntity getRequestEntityWithBodyParams(String accessToken, DanskeTransaction danskeTransaction){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);

        Map<String, Object> bodyParams = new HashMap<>();
        bodyParams.put("template", danskeTransaction.getCreditDebitIndicator().toLowerCase());
        bodyParams.put("amount", danskeTransaction.getTransactionAmount().getAmount());

        return new HttpEntity(bodyParams, httpHeaders);
    }

    public List<Account> getParsedAccountList(List<? extends Account> unparsedAccoutsList){
        return new ArrayList<>(unparsedAccoutsList);
    }
}
