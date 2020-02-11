package lt.lukasnakas.service.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.service.AccountService;
import lt.lukasnakas.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DanskeService implements AccountService, TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DanskeService.class);

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
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }

        return getParsedAccountList(responseEntity.getBody());
    }

    public List<Transaction> retrieveTransactions(){
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
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }

        return getParsedTransactionsList(responseEntity.getBody());
    }

    public Transaction postTransaction(Payment payment){
        ResponseEntity<DanskeTransaction> responseEntity;

        try {
            String accessToken = danskeServiceConfiguration.getAccessToken();
            responseEntity = restTemplate.exchange(
                    danskeServiceConfiguration.getUrlAccountTransactions(),
                    HttpMethod.POST,
                    getRequestEntityWithBodyParams(accessToken, payment),
                    DanskeTransaction.class);
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = danskeTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(
                    danskeServiceConfiguration.getUrlAccountTransactions(),
                    HttpMethod.POST,
                    getRequestEntityWithBodyParams(accessToken, payment),
                    DanskeTransaction.class);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }

        return responseEntity.getBody();
    }

    private HttpEntity getRequestEntity(String accessToken){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity(httpHeaders);
    }

    public HttpEntity getRequestEntityWithBodyParams(String accessToken, Payment payment){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity(payment, httpHeaders);
    }

    public List<Account> getParsedAccountList(List<? extends Account> unparsedAccoutsList){
        return new ArrayList<>(unparsedAccoutsList);
    }

    public List<Transaction> getParsedTransactionsList(List<? extends Transaction> unparsedTransactionsList){
        return new ArrayList<>(unparsedTransactionsList);
    }

    public String getBankName(){
        return danskeServiceConfiguration.getName();
    }
}
