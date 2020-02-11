package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.revolut.RevolutAccount;
import lt.lukasnakas.model.revolut.RevolutTransaction;
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
public class RevolutService implements AccountService {
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
            responseEntity = restTemplate.exchange(
                    revolutServiceConfiguration.getUrlAccounts(),
                    HttpMethod.GET,
                    getRequestEntity(accessToken),
                    new ParameterizedTypeReference<List<RevolutAccount>>() {});
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = revolutTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(
                    revolutServiceConfiguration.getUrlAccounts(),
                    HttpMethod.GET,
                    getRequestEntity(accessToken),
                    new ParameterizedTypeReference<List<RevolutAccount>>() {});
        }

        return getParsedAccountList(responseEntity.getBody());
    }

    public List<Transaction> retrieveTransactions() {
        ResponseEntity<List<RevolutTransaction>> responseEntity;

        try {
            String accessToken = revolutServiceConfiguration.getAccessToken();
            responseEntity = restTemplate.exchange(
                    revolutServiceConfiguration.getUrlAccountTransactions(),
                    HttpMethod.GET,
                    getRequestEntity(accessToken),
                    new ParameterizedTypeReference<List<RevolutTransaction>>() {});
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = revolutTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(
                    revolutServiceConfiguration.getUrlAccountTransactions(),
                    HttpMethod.GET,
                    getRequestEntity(accessToken),
                    new ParameterizedTypeReference<List<RevolutTransaction>>() {});
        }

        return getParsedTransactionsList(responseEntity.getBody());
    }

    private HttpEntity getRequestEntity(String accessToken) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity(httpHeaders);
    }

    public List<Account> getParsedAccountList(List<? extends Account> unparsedAccountsList){
        return new ArrayList<>(unparsedAccountsList);
    }

    public List<Transaction> getParsedTransactionsList(List<? extends Transaction> unparsedTransactionsList){
        return new ArrayList<>(unparsedTransactionsList);
    }
}
