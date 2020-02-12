package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import lt.lukasnakas.model.revolut.transaction.RevolutTransactionBase;
import lt.lukasnakas.model.revolut.transaction.RevolutTransfer;
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
public class RevolutService implements AccountService, TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RevolutService.class);

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
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
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
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }

        return getParsedTransactionsList(responseEntity.getBody());
    }

    public Transaction postTransaction(Payment payment){
        ResponseEntity<RevolutTransactionBase> responseEntity;

        try{
            String accessToken = revolutServiceConfiguration.getAccessToken();
            responseEntity = restTemplate.exchange(
                    getTransactionUrl(payment),
                    HttpMethod.POST,
                    getRequestEntityWithBodyParams(accessToken, payment),
                    RevolutTransactionBase.class);
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = revolutTokenRenewalService.generateAccessToken();
            responseEntity = restTemplate.exchange(
                    getTransactionUrl(payment),
                    HttpMethod.POST,
                    getRequestEntityWithBodyParams(accessToken, payment),
                    RevolutTransactionBase.class);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }

        return responseEntity.getBody();
    }

    private String getTransactionUrl(Payment payment) {
        if(payment.getClass().equals(RevolutTransfer.class))
            return revolutServiceConfiguration.getUrlAccountTranfer();
        else
            return revolutServiceConfiguration.getUrlAccountPayment();
    }

    private HttpEntity getRequestEntity(String accessToken) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity(httpHeaders);
    }

    private HttpEntity getRequestEntityWithBodyParams(String accessToken, Payment payment){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity(payment, httpHeaders);
    }

    public List<Account> getParsedAccountList(List<? extends Account> unparsedAccountsList){
        return new ArrayList<>(unparsedAccountsList);
    }

    public List<Transaction> getParsedTransactionsList(List<? extends Transaction> unparsedTransactionsList){
        return new ArrayList<>(unparsedTransactionsList);
    }

    public String getBankName(){
        return revolutServiceConfiguration.getName();
    }
}
