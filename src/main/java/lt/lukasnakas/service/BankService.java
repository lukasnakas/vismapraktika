package lt.lukasnakas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutReceiver;
import lt.lukasnakas.service.danske.DanskeService;
import lt.lukasnakas.service.revolut.RevolutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BankService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankService.class);

	@Autowired
	private DanskeService danskeService;

	@Autowired
	private RevolutService revolutService;

	private List<Account> getAllAccountsList() {
		List<Account> accountsList = new ArrayList<>();
		accountsList.addAll(danskeService.retrieveAccounts());
		accountsList.addAll(revolutService.retrieveAccounts());
		return accountsList;
	}

	public Map<String, Account> getAccounts() {
		Map<String, Account> accountsMap = new HashMap<>();

		for (Account account : getAllAccountsList())
			accountsMap.put(account.getId(), account);
		return accountsMap;
	}

	public Account getAccountById(String id){
		return getAccounts().get(id);
	}

	private List<Transaction> getAllTransactionsList() {
		List<Transaction> transactionsList = new ArrayList<>();
		transactionsList.addAll(danskeService.retrieveTransactions());
		transactionsList.addAll(revolutService.retrieveTransactions());
		return transactionsList;
	}

	public Map<String, Transaction> getTransactions() {
		Map<String, Transaction> transactionsMap = new HashMap<>();

		for (Transaction transaction : getAllTransactionsList())
			transactionsMap.put(transaction.getId(), transaction);
		return transactionsMap;
	}

	public Transaction getTransactionById(String id){
		return getTransactions().get(id);
	}

	public Transaction postTransaction(Payment payment, String bankName) {
		if (bankName.equalsIgnoreCase(danskeService.getBankName())) {
			//Payment payment = convertJsonToPaymentObject(paymentBody, DanskePayment.class);
			return danskeService.executeTransactionIfValid(payment);
		} else if (bankName.equalsIgnoreCase(revolutService.getBankName())) {
			//Payment payment = convertJsonToPaymentObject(paymentBody, RevolutPayment.class);
			return revolutService.executeTransactionIfValid(payment);
			//return executeSpecificRevolutTransactionType(paymentBody);
		}
		return getTransactionError("bankName");
	}

	private Payment convertJsonToPaymentObject(String paymentBody, Class<? extends Payment> paymentClass) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonNode = mapper.readTree(paymentBody);
			return mapper.convertValue(jsonNode, paymentClass);
		} catch (JsonProcessingException e) {
			LOGGER.warn(e.getMessage());
			return null;
		}
	}

	private TransactionError getTransactionError(String invalidKey){
		TransactionError transactionError = new TransactionError(invalidKey);
		String errorMsg = transactionError.toString();
		LOGGER.error(errorMsg);
		return transactionError;
	}
}