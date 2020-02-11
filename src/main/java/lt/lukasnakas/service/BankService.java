package lt.lukasnakas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.danske.DanskePayment;
import lt.lukasnakas.model.revolut.RevolutPayment;
import lt.lukasnakas.service.danske.DanskeService;
import lt.lukasnakas.service.revolut.RevolutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {

	private static final Logger logger = LoggerFactory.getLogger(BankService.class);

	@Autowired
	private DanskeService danskeService;

	@Autowired
	private RevolutService revolutService;

	public List<Account> getAccounts(){
		List<Account> accountsList = new ArrayList<>();
		accountsList.addAll(danskeService.retrieveAccounts());
		accountsList.addAll(revolutService.retrieveAccounts());
		return accountsList;
	}

	public List<Transaction> getTransactions(){
		List<Transaction> transactionsList = new ArrayList<>();
		transactionsList.addAll(danskeService.retrieveTransactions());
		transactionsList.addAll(revolutService.retrieveTransactions());
		return transactionsList;
	}

	public Transaction postTransaction(String paymentBody){
		String bankName = getBankName(paymentBody);

		if(bankName != null) {
			if (bankName.equalsIgnoreCase(danskeService.getBankName()))
				return danskeService.postTransaction(convertJsonToPayment(paymentBody, DanskePayment.class));
			else if (bankName.equalsIgnoreCase(revolutService.getBankName()))
				return revolutService.postTransaction(convertJsonToPayment(paymentBody, RevolutPayment.class));
		}

		return null;
	}

	private Payment convertJsonToPayment(String paymentBody, Class paymentClass){
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonNode = mapper.readTree(paymentBody);
			return (Payment) mapper.convertValue(jsonNode, paymentClass);
		} catch (JsonProcessingException e) {
			logger.warn(e.getMessage());
		}
		return null;
	}

	private String getBankName(String paymentBody){
		ObjectMapper mapper = new ObjectMapper();
		String bankName = null;

		try {
			JsonNode node = mapper.readTree(paymentBody);
			bankName = node.get("bankName").toString();
		} catch (JsonProcessingException e) {
			logger.warn(e.getMessage());
		}

		return bankName;
	}

}
