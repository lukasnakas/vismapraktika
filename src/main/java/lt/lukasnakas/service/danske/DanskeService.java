package lt.lukasnakas.service.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.transaction.DanskePayment;
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
            responseEntity = getResponseEntityForAccounts(accessToken);
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = danskeTokenRenewalService.generateAccessToken();
            responseEntity = getResponseEntityForAccounts(accessToken);
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
            responseEntity = getResponseEntityForTransactions(accessToken);
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = danskeTokenRenewalService.generateAccessToken();
            responseEntity = getResponseEntityForTransactions(accessToken);
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
            responseEntity = getResponseEntityForTransaction(accessToken, payment);
        } catch (HttpClientErrorException.Unauthorized e){
            String accessToken = danskeTokenRenewalService.generateAccessToken();
            responseEntity = getResponseEntityForTransaction(accessToken, payment);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }

        return responseEntity.getBody();
    }

    private ResponseEntity<List<DanskeAccount>> getResponseEntityForAccounts(String accessToken){
        return  restTemplate.exchange(
                danskeServiceConfiguration.getUrlAccounts(),
                HttpMethod.GET,
                getHttpEntity(accessToken),
                new ParameterizedTypeReference<List<DanskeAccount>>() {});
    }

    private ResponseEntity<List<DanskeTransaction>> getResponseEntityForTransactions(String accessToken){
        return  restTemplate.exchange(
                danskeServiceConfiguration.getUrlAccountTransactions(),
                HttpMethod.GET,
                getHttpEntity(accessToken),
                new ParameterizedTypeReference<List<DanskeTransaction>>() {});
    }

    private ResponseEntity<DanskeTransaction> getResponseEntityForTransaction(String accessToken, Payment payment){
        return  restTemplate.exchange(
                danskeServiceConfiguration.getUrlAccountTransactions(),
                HttpMethod.POST,
                getHttpEntity(accessToken, payment),
                DanskeTransaction.class);
    }

    private HttpEntity<String> getHttpEntity(String accessToken){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity<>(httpHeaders);
    }

    private HttpEntity<?> getHttpEntity(String accessToken, Payment payment){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity<>(payment, httpHeaders);
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

    public boolean isPaymentValid(Payment payment){
        DanskePayment danskePayment = (DanskePayment) payment;
        if(danskePayment.getBankName() == null) return false;
        if(danskePayment.getTemplate() == null) return false;
        if(danskePayment.getAmount() <= 0)      return false;
        return true;
    }
}