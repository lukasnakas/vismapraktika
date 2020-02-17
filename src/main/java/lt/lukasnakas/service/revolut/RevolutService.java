package lt.lukasnakas.service.revolut;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutReceiver;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
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
	private RevolutPaymentValidationService revolutPaymentValidationService;

	@Autowired
	private RevolutTransactionErrorService revolutTransactionErrorService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HttpHeaders httpHeaders;

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
			return new ArrayList<>();
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
			return new ArrayList<>();
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
			return null;
		}

		log("POST", "transaction", responseEntity);
		System.out.println(responseEntity.getBody());
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
		System.out.println(payment);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String node = mapper.writeValueAsString(payment);
			System.out.println(node);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
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
		RevolutPayment revolutPayment = new RevolutPayment(payment.getSenderAccountId(), revolutReceiver, payment.getCurrency(),
				payment.getDescription(), payment.getAmount());

		if (isPaymentValid(revolutPayment))
			return postTransaction(getPaymentWithGeneratedRequestId(revolutPayment));
		else {
			TransactionError transactionError = getErrorWithMissingsParamFromPayment(revolutPayment);
			String errorMsg = transactionError.toString();
			LOGGER.error(errorMsg);
			return transactionError;
		}
	}

	private RevolutPayment getPaymentWithGeneratedRequestId(RevolutPayment revolutPayment) {
		revolutPayment.generateRequestId();
		return revolutPayment;
	}

}