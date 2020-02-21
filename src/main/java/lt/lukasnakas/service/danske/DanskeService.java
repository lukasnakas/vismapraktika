package lt.lukasnakas.service.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.exception.*;
import lt.lukasnakas.model.*;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.service.BankingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DanskeService implements BankingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DanskeService.class);

    private final DanskeServiceConfiguration danskeServiceConfiguration;
    private final DanskeTokenRenewalService danskeTokenRenewalService;
    private final DanskePaymentValidationService danskePaymentValidationService;
    private final DanskeTransactionErrorService danskeTransactionErrorService;
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    public DanskeService(DanskeServiceConfiguration danskeServiceConfiguration,
                         DanskeTokenRenewalService danskeTokenRenewalService,
                         DanskePaymentValidationService danskePaymentValidationService,
                         DanskeTransactionErrorService danskeTransactionErrorService,
                         RestTemplate restTemplate,
                         HttpHeaders httpHeaders) {
        this.danskeServiceConfiguration = danskeServiceConfiguration;
        this.danskeTokenRenewalService = danskeTokenRenewalService;
        this.danskePaymentValidationService = danskePaymentValidationService;
        this.danskeTransactionErrorService = danskeTransactionErrorService;
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    public List<CommonAccount> retrieveAccounts() {
        ResponseEntity<DanskeAccount> responseEntity;

        try {
            responseEntity = getResponseEntityForAccounts();
        } catch (HttpClientErrorException.Unauthorized e) {
            responseEntity = getResponseEntityForAccounts();
        } catch (Exception e) {
            throw new AccountRetrievalException(e.getMessage());
        }

        log("GET", "accounts", responseEntity);

        return Collections.singletonList(
                convertToCommonAccount(Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new AccountRetrievalException("Failed to retrieve accounts"))));
    }

    private CommonAccount convertToCommonAccount(DanskeAccount danskeAccount){
        return new CommonAccount("Danske",
                danskeAccount.getData().getBalance()[0].getAccountId(),
                danskeAccount.getData().getBalance()[0].getAmount().getAmount(),
                danskeAccount.getData().getBalance()[0].getAmount().getCurrency());
    }

    public List<CommonTransaction> retrieveTransactions() {
        ResponseEntity<List<DanskeTransaction>> responseEntity;

        try {
            String accessToken = danskeServiceConfiguration.getAccessToken();
            responseEntity = getResponseEntityForTransactions(accessToken);
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = danskeTokenRenewalService.generateAccessToken().getToken();
            responseEntity = getResponseEntityForTransactions(accessToken);
        } catch (Exception e) {
            throw new TransactionRetrievalException(e.getMessage());
        }

        log("GET", "transactions", responseEntity);

        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new TransactionRetrievalException("Failed to retrieve transactions"))
                .stream()
                .map(this::convertToCommonTransaction)
                .collect(Collectors.toList());
    }

    private CommonTransaction convertToCommonTransaction(DanskeTransaction danskeTransaction){
        return new CommonTransaction(danskeTransaction.getId(),
                danskeTransaction.getAccountId(),
                null,
                danskeTransaction.getTransactionAmount().getAmount(),
                danskeTransaction.getTransactionAmount().getCurrency());
    }

    public CommonTransaction postTransaction(Payment payment) {
        ResponseEntity<DanskeTransaction> responseEntity;

        try {
            String accessToken = danskeServiceConfiguration.getAccessToken();
            responseEntity = getResponseEntityForTransaction(accessToken, payment);
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = danskeTokenRenewalService.generateAccessToken().getToken();
            responseEntity = getResponseEntityForTransaction(accessToken, payment);
        } catch (Exception e) {
            throw new TransactionExecutionExeption(e.getMessage());
        }

        log("POST", "transaction", responseEntity);
        return convertToCommonTransaction(Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new TransactionExecutionExeption("Failed to execute transaction")));
    }

    private void log(String method, String object, ResponseEntity<?> responseEntity) {
        LOGGER.info("[{}] {} {} [Status Code: {}]",
                danskeServiceConfiguration.getName(),
                method,
                object,
                responseEntity.getStatusCode());
    }

    private ResponseEntity<DanskeAccount> getResponseEntityForAccounts() {
        return restTemplate.exchange(
                danskeServiceConfiguration.getUrlAccountsVirtual(),
                HttpMethod.GET,
                getHttpEntityVirtual(),
                new ParameterizedTypeReference<DanskeAccount>() {
                });
    }

    private ResponseEntity<List<DanskeTransaction>> getResponseEntityForTransactions(String accessToken) {
        return restTemplate.exchange(
                danskeServiceConfiguration.getUrlAccountTransactions(),
                HttpMethod.GET,
                getHttpEntity(accessToken),
                new ParameterizedTypeReference<List<DanskeTransaction>>() {
                });
    }

    private ResponseEntity<DanskeTransaction> getResponseEntityForTransaction(String accessToken, Payment payment) {
        return restTemplate.exchange(
                danskeServiceConfiguration.getUrlAccountTransactions(),
                HttpMethod.POST,
                getHttpEntity(accessToken, payment),
                DanskeTransaction.class);
    }

    private HttpEntity<String> getHttpEntity(String accessToken) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity<>(httpHeaders);
    }

    private HttpEntity<String> getHttpEntityVirtual() {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", danskeServiceConfiguration.getAccessTokenVirtual());
        httpHeaders.add("x-fapi-financial-id", "0015800000jf7AeAAI");
        return new HttpEntity<>(httpHeaders);
    }

    private HttpEntity<?> getHttpEntity(String accessToken, Payment payment) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        return new HttpEntity<>(payment, httpHeaders);
    }

    public List<Account> getParsedAccountsList(List<? extends Account> unparsedAccoutsList) {
        return new ArrayList<>(unparsedAccoutsList);
    }

    public List<Transaction> getParsedTransactionsList(List<? extends Transaction> unparsedTransactionsList) {
        return new ArrayList<>(unparsedTransactionsList);
    }

    public String getBankName() {
        return danskeServiceConfiguration.getName();
    }

    public boolean isPaymentValid(Payment payment) {
        return danskePaymentValidationService.isValid(payment);
    }

    public TransactionError getErrorWithFirstMissingParamFromPayment(Payment payment) {
        return danskeTransactionErrorService.getErrorWithMissingParamsFromPayment(payment);
    }

    public CommonTransaction executeTransactionIfValid(Payment payment) {
        if (isPaymentValid(payment)) {
            return postTransaction(payment);
        } else {
            TransactionError transactionError = getErrorWithFirstMissingParamFromPayment(payment);
            throw new BadRequestException(transactionError.getMessage());
        }
    }
}