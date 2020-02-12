package lt.lukasnakas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.danske.transaction.DanskePayment;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutTransfer;
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

	private List<Account> getAllAccountsList(){
		List<Account> accountsList = new ArrayList<>();
		accountsList.addAll(danskeService.retrieveAccounts());
		accountsList.addAll(revolutService.retrieveAccounts());
		return accountsList;
	}

	public Map<String, Account> getAccounts(){
		Map<String, Account> accountsMap = new HashMap<>();

		for(Account account : getAllAccountsList())
			accountsMap.put(account.getId(), account);

		return accountsMap;
	}

	private List<Transaction> getAllTransactionsList(){
		List<Transaction> transactionsList = new ArrayList<>();
		transactionsList.addAll(danskeService.retrieveTransactions());
		transactionsList.addAll(revolutService.retrieveTransactions());
		return transactionsList;
	}

	public Map<String, Transaction> getTransactions(){
		Map<String, Transaction> transactionsMap = new HashMap<>();

		for(Transaction transaction : getAllTransactionsList())
			transactionsMap.put(transaction.getId(), transaction);

		return transactionsMap;
	}

	public Transaction postTransaction(String paymentBody){
		String bankName = getBankName(paymentBody);

		if(bankName != null)
			return executeTransactionInSpecificBank(bankName, paymentBody);
		return null;
	}

	private Transaction executeTransactionInSpecificBank(String bankName, String paymentBody){
		if (bankName.equalsIgnoreCase(danskeService.getBankName()))
			return danskeService.postTransaction(convertJsonToPaymentObject(paymentBody, DanskePayment.class));
		else if (bankName.equalsIgnoreCase(revolutService.getBankName()))
			return executeSpecificRevolutTransactionType(paymentBody);
		return null;
	}

	private Transaction executeSpecificRevolutTransactionType(String paymentBody){
		String paymentType = getPaymentType(paymentBody);
		if(paymentType != null) {
			if (paymentType.equalsIgnoreCase("\"payment\""))
				return revolutService.postTransaction(convertJsonToPaymentObject(paymentBody, RevolutPayment.class));
			else if (paymentType.equalsIgnoreCase("\"transfer\""))
				return revolutService.postTransaction(convertJsonToPaymentObject(paymentBody, RevolutTransfer.class));
		}
		return null;
	}

	private Payment convertJsonToPaymentObject(String paymentBody, Class<? extends Payment> paymentClass){
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonNode = mapper.readTree(paymentBody);
			return mapper.convertValue(jsonNode, paymentClass);
		} catch (JsonProcessingException e) {
			LOGGER.warn(e.getMessage());
			return null;
		}
	}

	private String getBankName(String paymentBody){
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(paymentBody);
			return node.get("bankName").toString();
		} catch (JsonProcessingException e) {
			LOGGER.warn(e.getMessage());
			return null;
		}
	}

	private String getPaymentType(String paymentBody){
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(paymentBody);
			return node.get("type").toString();
		} catch (JsonProcessingException e) {
			LOGGER.warn(e.getMessage());
			return null;
		}
	}

}
