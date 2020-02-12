package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
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
			responseEntity = getResponseEntityForAccounts(accessToken);
		} catch (HttpClientErrorException.Unauthorized e) {
			String accessToken = revolutTokenRenewalService.generateAccessToken();
			responseEntity = getResponseEntityForAccounts(accessToken);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new ArrayList<>();
		}

		return getParsedAccountList(responseEntity.getBody());
	}

	public List<Transaction> retrieveTransactions() {
		ResponseEntity<List<RevolutTransaction>> responseEntity;

		try {
			String accessToken = revolutServiceConfiguration.getAccessToken();
			responseEntity = getResponseEntityForTransactions(accessToken);
		} catch (HttpClientErrorException.Unauthorized e) {
			String accessToken = revolutTokenRenewalService.generateAccessToken();
			responseEntity = getResponseEntityForTransactions(accessToken);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new ArrayList<>();
		}

		return getParsedTransactionsList(responseEntity.getBody());
	}

	public Transaction postTransaction(Payment payment) {
		ResponseEntity<RevolutTransactionBase> responseEntity;

		try {
			String accessToken = revolutServiceConfiguration.getAccessToken();
			responseEntity = getResponseEntityForTransaction(accessToken, payment);
		} catch (HttpClientErrorException.Unauthorized e) {
			String accessToken = revolutTokenRenewalService.generateAccessToken();
			responseEntity = getResponseEntityForTransaction(accessToken, payment);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}

		return responseEntity.getBody();
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

	private ResponseEntity<RevolutTransactionBase> getResponseEntityForTransaction(String accessToken, Payment payment) {
		return restTemplate.exchange(
				getTransactionUrl(payment),
				HttpMethod.POST,
				getHttpEntity(accessToken, payment),
				RevolutTransactionBase.class);
	}

	private String getTransactionUrl(Payment payment) {
		if (payment.getClass().equals(RevolutTransfer.class))
			return revolutServiceConfiguration.getUrlAccountTranfer();
		else
			return revolutServiceConfiguration.getUrlAccountPayment();
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

	public List<Account> getParsedAccountList(List<? extends Account> unparsedAccountsList) {
		return new ArrayList<>(unparsedAccountsList);
	}

	public List<Transaction> getParsedTransactionsList(List<? extends Transaction> unparsedTransactionsList) {
		return new ArrayList<>(unparsedTransactionsList);
	}

	public String getBankName() {
		return revolutServiceConfiguration.getName();
	}

	public boolean isPaymentValid(Payment payment) {
		if (payment.getClass() == RevolutPayment.class) {
			RevolutPayment revolutPayment = (RevolutPayment) payment;
			return isRevolutPaymentValid(revolutPayment);
		} else {
			RevolutTransfer revolutTransfer = (RevolutTransfer) payment;
			return isRevolutTransferValid(revolutTransfer);
		}
	}

	private boolean isRevolutPaymentValid(RevolutPayment revolutPayment) {
		if (revolutPayment.getRequestId() == null) return false;
		if (revolutPayment.getAccountId() == null) return false;
		if (revolutPayment.getReceiver() == null) return false;
		if (revolutPayment.getReceiver().getAccountId() == null) return false;
		if (revolutPayment.getReceiver().getCounterPartyId() == null) return false;
		if (revolutPayment.getReference() == null) return false;
		if (revolutPayment.getCurrency() == null) return false;
		if (revolutPayment.getAmount() <= 0) return false;
		return true;
	}

	private boolean isRevolutTransferValid(RevolutTransfer revolutTransfer) {
		if (revolutTransfer.getRequestId() == null) return false;
		if (revolutTransfer.getSourceAccountId() == null) return false;
		if (revolutTransfer.getTargetAccountId() == null) return false;
		if (revolutTransfer.getDescription() == null) return false;
		if (revolutTransfer.getCurrency() == null) return false;
		if (revolutTransfer.getAmount() <= 0) return false;
		return true;
	}
}