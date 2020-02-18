package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.exception.*;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutReceiver;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import lt.lukasnakas.service.BankingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RevolutService implements BankingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RevolutService.class);

    private final RevolutServiceConfiguration revolutServiceConfiguration;
    private final RevolutTokenRenewalService revolutTokenRenewalService;
    private final RevolutPaymentValidationService revolutPaymentValidationService;
    private final RevolutTransactionErrorService revolutTransactionErrorService;
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    public RevolutService(RevolutServiceConfiguration revolutServiceConfiguration,
                          RevolutTokenRenewalService revolutTokenRenewalService,
                          RevolutPaymentValidationService revolutPaymentValidationService,
                          RevolutTransactionErrorService revolutTransactionErrorService,
                          RestTemplate restTemplate,
                          HttpHeaders httpHeaders) {
        this.revolutServiceConfiguration = revolutServiceConfiguration;
        this.revolutTokenRenewalService = revolutTokenRenewalService;
        this.revolutPaymentValidationService = revolutPaymentValidationService;
        this.revolutTransactionErrorService = revolutTransactionErrorService;
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    public List<Account> retrieveAccounts() {
        ResponseEntity<List<RevolutAccount>> responseEntity;

        try {
            String accessToken = revolutServiceConfiguration.getAccessToken();
            responseEntity = getResponseEntityForAccounts(accessToken);
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = revolutTokenRenewalService.generateAccessToken().getToken();
            responseEntity = getResponseEntityForAccounts(accessToken);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new AccountRetrievalException(e.getMessage());
        }

        log("GET", "accounts", responseEntity);
        return getParsedAccountsList(responseEntity.getBody());
    }

    public List<Transaction> retrieveTransactions() {
        ResponseEntity<List<RevolutTransaction>> responseEntity;

        try {
            String accessToken = revolutServiceConfiguration.getAccessToken();
            responseEntity = getResponseEntityForTransactions(accessToken);
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = revolutTokenRenewalService.generateAccessToken().getToken();
            responseEntity = getResponseEntityForTransactions(accessToken);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new TransactionRetrievalException(e.getMessage());
        }

        log("GET", "transactions", responseEntity);
        return getParsedTransactionsList(responseEntity.getBody());
    }

    public Transaction postTransaction(Payment payment) {
        ResponseEntity<RevolutTransaction> responseEntity;

        try {
            String accessToken = revolutServiceConfiguration.getAccessToken();
            responseEntity = getResponseEntityForTransaction(accessToken, payment);
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = revolutTokenRenewalService.generateAccessToken().getToken();
            responseEntity = getResponseEntityForTransaction(accessToken, payment);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new TransactionExecutionExeption(e.getMessage());
        }

        log("POST", "transaction", responseEntity);
        return responseEntity.getBody();
    }

    private void log(String method, String object, ResponseEntity<?> responseEntity) {
        LOGGER.info("[{}] {} {} [Status Code: {}]",
                revolutServiceConfiguration.getName(),
                method,
                object,
                responseEntity.getStatusCode());
    }

    private ResponseEntity<List<RevolutAccount>> getResponseEntityForAccounts(String accessToken) {
        return restTemplate.exchange(
                revolutServiceConfiguration.getUrlAccounts(),
                HttpMethod.GET,
                getHttpEntity(accessToken),
                new ParameterizedTypeReference<List<RevolutAccount>>() {
                });
    }

    private ResponseEntity<List<RevolutTransaction>> getResponseEntityForTransactions(String accessToken) {
        return restTemplate.exchange(
                revolutServiceConfiguration.getUrlAccountTransactions(),
                HttpMethod.GET,
                getHttpEntity(accessToken),
                new ParameterizedTypeReference<List<RevolutTransaction>>() {
                });
    }

    private ResponseEntity<RevolutTransaction> getResponseEntityForTransaction(String accessToken, Payment payment) {
        return restTemplate.exchange(
                revolutServiceConfiguration.getUrlAccountPayment(),
                HttpMethod.POST,
                getHttpEntity(accessToken, payment),
                RevolutTransaction.class);
    }

    private HttpEntity<String> getHttpEntity(String accessToken) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity<>(httpHeaders);
    }

    private HttpEntity<?> getHttpEntity(String accessToken, Payment payment) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity<>(payment, httpHeaders);
    }

    public List<Account> getParsedAccountsList(List<? extends Account> unparsedAccountsList) {
        return new ArrayList<>(unparsedAccountsList);
    }

    public List<Transaction> getParsedTransactionsList(List<? extends Transaction> unparsedTransactionsList) {
        return new ArrayList<>(unparsedTransactionsList);
    }

    public String getBankName() {
        return revolutServiceConfiguration.getName();
    }

    public boolean isPaymentValid(Payment payment) {
        return revolutPaymentValidationService.isValid(payment);
    }

    public TransactionError getErrorWithMissingsParamFromPayment(Payment payment) {
        return revolutTransactionErrorService.getErrorWithMissingParamsFromPayment(payment);
    }

    public Transaction executeTransactionIfValid(Payment payment) {
        RevolutReceiver revolutReceiver = new RevolutReceiver(payment.getCounterpartyId(), payment.getReceiverAccountId());
        RevolutPayment revolutPayment = new RevolutPayment(payment.getSenderAccountId(), revolutReceiver,
                payment.getCurrency(), payment.getDescription(), payment.getAmount());

        if (isPaymentValid(revolutPayment)) {
            revolutPayment.setGeneratedRequestId();
            return postTransaction(revolutPayment);
        } else {
            TransactionError transactionError = getErrorWithMissingsParamFromPayment(revolutPayment);
            throw new BadRequestException(transactionError.getMessage());
        }
    }
}