package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.mapper.CommonAccountMapper;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.exception.AccountRetrievalException;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.TransactionExecutionExeption;
import lt.lukasnakas.exception.TransactionRetrievalException;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutReceiver;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import lt.lukasnakas.service.BankingService;
import lt.lukasnakas.service.CommonEntityMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RevolutService implements BankingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RevolutService.class);

    private final RevolutServiceConfiguration revolutServiceConfiguration;
    private final RevolutTokenRenewalService revolutTokenRenewalService;
    private final RevolutPaymentValidationService revolutPaymentValidationService;
    private final RevolutTransactionErrorService revolutTransactionErrorService;
    private final CommonEntityMapperService commonEntityMapperService;
    private final CommonAccountMapper commonAccountMapper;
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    public RevolutService(RevolutServiceConfiguration revolutServiceConfiguration,
                          RevolutTokenRenewalService revolutTokenRenewalService,
                          RevolutPaymentValidationService revolutPaymentValidationService,
                          RevolutTransactionErrorService revolutTransactionErrorService,
                          CommonEntityMapperService commonEntityMapperService,
                          CommonAccountMapper commonAccountMapper,
                          RestTemplate restTemplate,
                          HttpHeaders httpHeaders) {
        this.revolutServiceConfiguration = revolutServiceConfiguration;
        this.revolutTokenRenewalService = revolutTokenRenewalService;
        this.revolutPaymentValidationService = revolutPaymentValidationService;
        this.revolutTransactionErrorService = revolutTransactionErrorService;
        this.commonEntityMapperService = commonEntityMapperService;
        this.commonAccountMapper = commonAccountMapper;
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    public List<CommonAccount> retrieveAccounts() {
        ResponseEntity<List<RevolutAccount>> responseEntity;

        try {
            String accessToken = revolutServiceConfiguration.getAccessToken();
            responseEntity = getResponseEntityForAccounts(accessToken);
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = revolutTokenRenewalService.generateAccessToken().getToken();
            responseEntity = getResponseEntityForAccounts(accessToken);
        } catch (Exception e) {
            throw new AccountRetrievalException(e.getMessage());
        }

        log("GET", "accounts", responseEntity);
        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new AccountRetrievalException("Failed to retrieve accounts"))
                .stream()
                .map(commonAccountMapper::revolutAccountToCommonAccount)
                .collect(Collectors.toList());
    }

    public List<CommonTransaction> retrieveTransactions() {
        ResponseEntity<List<RevolutTransaction>> responseEntity;

        try {
            String accessToken = revolutServiceConfiguration.getAccessToken();
            responseEntity = getResponseEntityForTransactions(accessToken);
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = revolutTokenRenewalService.generateAccessToken().getToken();
            responseEntity = getResponseEntityForTransactions(accessToken);
        } catch (Exception e) {
            throw new TransactionRetrievalException(e.getMessage());
        }

        log("GET", "transactions", responseEntity);
        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new TransactionRetrievalException("Failed to retrieve transactions"))
                .stream()
                .filter(commonEntityMapperService::hasCounterparty)
                .map(commonEntityMapperService::convertToCommonTransaction)
                .collect(Collectors.toList());
    }

    public CommonTransaction postTransaction(Payment payment) {
        ResponseEntity<RevolutTransaction> responseEntity;

        try {
            String accessToken = revolutServiceConfiguration.getAccessToken();
            responseEntity = getResponseEntityForTransaction(accessToken, payment);
        } catch (HttpClientErrorException.Unauthorized e) {
            String accessToken = revolutTokenRenewalService.generateAccessToken().getToken();
            responseEntity = getResponseEntityForTransaction(accessToken, payment);
        } catch (Exception e) {
            throw new TransactionExecutionExeption(e.getMessage());
        }

        log("POST", "transaction", responseEntity);
        return commonEntityMapperService.convertToCommonTransaction(
                Optional.ofNullable(responseEntity.getBody())
                        .orElseThrow(() -> new TransactionExecutionExeption("Failed to execute transaction")),
                payment);
    }

    private void log(String method, String object, ResponseEntity<?> responseEntity) {
        LOGGER.info("[{}] {} {} [Status Code: {}]",
                revolutServiceConfiguration.getName(),
                method,
                object,
                responseEntity.getStatusCode());
    }

    public ResponseEntity<List<RevolutAccount>> getResponseEntityForAccounts(String accessToken) {
        return restTemplate.exchange(
                revolutServiceConfiguration.getUrlAccounts(),
                HttpMethod.GET,
                getHttpEntity(accessToken),
                new ParameterizedTypeReference<List<RevolutAccount>>() {
                });
    }

    public ResponseEntity<List<RevolutTransaction>> getResponseEntityForTransactions(String accessToken) {
        return restTemplate.exchange(
                revolutServiceConfiguration.getUrlAccountTransactions(),
                HttpMethod.GET,
                getHttpEntity(accessToken),
                new ParameterizedTypeReference<List<RevolutTransaction>>() {
                });
    }

    public ResponseEntity<RevolutTransaction> getResponseEntityForTransaction(String accessToken, Payment payment) {
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

    public String getBankName() {
        return revolutServiceConfiguration.getName();
    }

    public boolean isPaymentValid(Payment payment) {
        return revolutPaymentValidationService.isValid(payment);
    }

    public TransactionError getErrorWithMissingParamsFromPayment(Payment payment) {
        return revolutTransactionErrorService.getErrorWithMissingParamsFromPayment(payment);
    }

    public CommonTransaction executeTransactionIfValid(Payment payment) {
        RevolutReceiver revolutReceiver = new RevolutReceiver(payment.getCounterpartyId(), payment.getReceiverAccountId());
        RevolutPayment revolutPayment = new RevolutPayment(payment.getSenderAccountId(), revolutReceiver,
                payment.getCurrency(), payment.getDescription(), payment.getAmount());

        if (isPaymentValid(revolutPayment)) {
            revolutPayment.setGeneratedRequestId();
            return postTransaction(revolutPayment);
        } else {
            TransactionError transactionError = getErrorWithMissingParamsFromPayment(revolutPayment);
            throw new BadRequestException(transactionError.getMessage());
        }
    }
}