package lt.lukasnakas.util;

import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.AccessToken;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.danske.account.Balance;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.account.Data;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.danske.transaction.DanskeTransactionAmount;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.model.revolut.RevolutAccessToken;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

public class TestDataGenerator {

	public ResponseEntity<DanskeAccount> getExpectedDanskeAccountResponseEntity() {
		DanskeTransactionAmount danskeTransactionAmount = new DanskeTransactionAmount();
		danskeTransactionAmount.setAmount(55);
		danskeTransactionAmount.setCurrency("EUR");


		Balance[] balances = new Balance[1];
		balances[0] = new Balance();
		balances[0].setAccountId("123");
		balances[0].setCreditDebitIndicator("debit");
		balances[0].setType("type");
		balances[0].setDatetime("2020");
		balances[0].setAmount(danskeTransactionAmount);

		Data data = new Data();
		data.setBalance(balances);

		DanskeAccount danskeAccount = new DanskeAccount();
		danskeAccount.setId("456");
		danskeAccount.setData(data);

		return new ResponseEntity<>(danskeAccount, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<List<RevolutAccount>> getExpectedRevolutAccountResponseEntity() {
		RevolutAccount revolutAccount = new RevolutAccount();
		revolutAccount.setId("123");
		revolutAccount.setName("name");
		revolutAccount.setBalance(10);
		revolutAccount.setCurrency("GBP");
		revolutAccount.setState("state");
		revolutAccount.setPublicAccount(true);
		revolutAccount.setCreatedAt("2020");
		revolutAccount.setUpdatedAt("2020");

		List<RevolutAccount> revolutAccountList = Collections.singletonList(revolutAccount);
		return new ResponseEntity<>(revolutAccountList, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<AccessToken> getExpectedDanskeAccessTokenResponseEntity() {
		AccessToken accessToken = new AccessToken();
		accessToken.setToken("123456789");

		return new ResponseEntity<>(accessToken, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<RevolutAccessToken> getExpectedRevolutAccessTokenResponseEntity() {
		RevolutAccessToken revolutAccessToken = new RevolutAccessToken();
		revolutAccessToken.setToken("123456789");
		revolutAccessToken.setTokenType("bearer");
		revolutAccessToken.setExpiresIn("2020");

		return new ResponseEntity<>(revolutAccessToken, HttpStatus.ACCEPTED);
	}

	private RevolutTransaction buildRevolutTransaction() {
		RevolutCounterparty revolutCounterparty = new RevolutCounterparty();
		revolutCounterparty.setAccountType("type");
		revolutCounterparty.setAccountId("123");

		RevolutTransactionLegs[] revolutTransactionLegs = new RevolutTransactionLegs[1];
		revolutTransactionLegs[0] = new RevolutTransactionLegs();
		revolutTransactionLegs[0].setId("123");
		revolutTransactionLegs[0].setAccountId("456");
		revolutTransactionLegs[0].setCounterparty(revolutCounterparty);
		revolutTransactionLegs[0].setAmount(10);
		revolutTransactionLegs[0].setCurrency("GBP");
		revolutTransactionLegs[0].setDescription("desc");
		revolutTransactionLegs[0].setBalance(100);

		RevolutTransaction revolutTransaction = new RevolutTransaction();
		revolutTransaction.setId("123");
		revolutTransaction.setType("type");
		revolutTransaction.setRequestId("456");
		revolutTransaction.setCreatedAt("2020");
		revolutTransaction.setUpdatedAt("2020");
		revolutTransaction.setCompletedAt("2020");
		revolutTransaction.setLegs(revolutTransactionLegs);
		revolutTransaction.setReference("ref");
		revolutTransaction.setState("active");

		return revolutTransaction;
	}

	private DanskeTransaction buildDanskeTransaction() {
		DanskeTransactionAmount danskeTransactionAmount = new DanskeTransactionAmount();
		danskeTransactionAmount.setAmount(5);
		danskeTransactionAmount.setCurrency("EUR");

		DanskeTransaction danskeTransaction = new DanskeTransaction();
		danskeTransaction.setId("123");
		danskeTransaction.setAccountId("456");
		danskeTransaction.setCreditDebitIndicator("debit");
		danskeTransaction.setTransactionAmount(danskeTransactionAmount);

		return danskeTransaction;
	}

	public CommonAccount buildCommonAccount(DanskeAccount danskeAccount) {
		CommonAccount commonAccount = new CommonAccount();
		commonAccount.setBankName("Danske");
		commonAccount.setAccountId(danskeAccount.getData().getBalance()[0].getAccountId());
		commonAccount.setCurrency(danskeAccount.getData().getBalance()[0].getAmount().getCurrency());
		commonAccount.setBalance(danskeAccount.getData().getBalance()[0].getAmount().getAmount());

		return commonAccount;
	}

	public CommonAccount buildCommonAccount(RevolutAccount revolutAccount) {
		CommonAccount commonAccount = new CommonAccount();
		commonAccount.setBankName("Revolut");
		commonAccount.setAccountId(revolutAccount.getId());
		commonAccount.setCurrency(revolutAccount.getCurrency());
		commonAccount.setBalance(revolutAccount.getBalance());

		return commonAccount;
	}

	public ResponseEntity<List<DanskeTransaction>> getExpectedDanskeTransactionResponseEntity() {
		List<DanskeTransaction> danskeTransactionList = Collections.singletonList(buildDanskeTransaction());
		return new ResponseEntity<>(danskeTransactionList, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<List<RevolutTransaction>> getExpectedRevolutTransactionResponseEntity() {
		List<RevolutTransaction> revolutTransactionList = Collections.singletonList(buildRevolutTransaction());
		return new ResponseEntity<>(revolutTransactionList, HttpStatus.ACCEPTED);
	}

	public CommonTransaction buildCommonTransaction(DanskeTransaction danskeTransaction) {
		CommonTransaction commonTransaction = new CommonTransaction();
		commonTransaction.setId(danskeTransaction.getId());
		commonTransaction.setSenderAccountId(null);
		commonTransaction.setReceiverAccountId(danskeTransaction.getAccountId());
		commonTransaction.setAmount(danskeTransaction.getTransactionAmount().getAmount());
		commonTransaction.setCurrency(danskeTransaction.getTransactionAmount().getCurrency());

		return commonTransaction;
	}

	public CommonTransaction buildCommonTransaction(RevolutTransaction revolutTransaction, Payment payment) {
		RevolutPayment revolutPayment = (RevolutPayment) payment;

		CommonTransaction commonTransaction = new CommonTransaction();
		commonTransaction.setId(revolutTransaction.getId());
		commonTransaction.setSenderAccountId(revolutPayment.getAccountId());
		commonTransaction.setReceiverAccountId(revolutPayment.getReceiver().getAccountId());
		commonTransaction.setAmount(revolutPayment.getAmount());
		commonTransaction.setCurrency(revolutPayment.getCurrency());

		return commonTransaction;
	}

	public CommonTransaction buildCommonTransaction(RevolutTransaction revolutTransaction) {
		CommonTransaction commonTransaction = new CommonTransaction();
		commonTransaction.setId(revolutTransaction.getId());
		commonTransaction.setSenderAccountId(revolutTransaction.getLegs()[0].getAccountId());
		commonTransaction.setReceiverAccountId(revolutTransaction.getLegs()[0].getCounterparty().getAccountId());
		commonTransaction.setAmount(revolutTransaction.getLegs()[0].getAmount());
		commonTransaction.setCurrency(revolutTransaction.getLegs()[0].getCurrency());

		return commonTransaction;
	}

	public CommonTransaction buildCommonTransaction() {
		CommonTransaction commonTransaction = new CommonTransaction();
		commonTransaction.setId("123");
		commonTransaction.setSenderAccountId("456");
		commonTransaction.setReceiverAccountId("789");
		commonTransaction.setAmount(10);
		commonTransaction.setCurrency("EUR");

		return commonTransaction;
	}

	public ResponseEntity<DanskeTransaction> getExpectedDanskeTransactionResponseEntityForPost() {
		return new ResponseEntity<>(buildDanskeTransaction(), HttpStatus.ACCEPTED);
	}

	public ResponseEntity<RevolutTransaction> getExpectedRevolutTransactionResponseEntityForPost() {
		return new ResponseEntity<>(buildRevolutTransaction(), HttpStatus.ACCEPTED);
	}

	public Payment buildTransactionPayment() {
		Payment payment = new Payment();
		payment.setSenderAccountId("123");
		payment.setReceiverAccountId("456");
		payment.setCounterpartyId("789");
		payment.setAmount(100);
		payment.setCurrency("EUR");
		payment.setDescription("desc");

		return payment;
	}

	public Payment buildInvalidTransactionPayment() {
		Payment payment = new Payment();
		payment.setSenderAccountId("123");
		payment.setReceiverAccountId("456");
		payment.setCounterpartyId("789");
		payment.setAmount(0);
		payment.setCurrency("EUR");
		payment.setDescription("desc");

		return payment;
	}

	public PaymentDTO buildInvalidTransactionPaymentDto() {
		PaymentDTO payment = new PaymentDTO();
		payment.setSenderAccountId("123");
		payment.setReceiverAccountId("456");
		payment.setCounterpartyId("789");
		payment.setAmount(0);
		payment.setCurrency("EUR");
		payment.setDescription("desc");

		return payment;
	}

	public RevolutPayment buildInvalidRevolutTransactionPayment() {
		RevolutReceiver revolutReceiver = new RevolutReceiver();
		revolutReceiver.setAccountId("123");
		revolutReceiver.setCounterPartyId("456");

		RevolutPayment revolutPayment = new RevolutPayment();
		revolutPayment.setAccountId(null);
		revolutPayment.setReceiver(revolutReceiver);
		revolutPayment.setCurrency("GBP");
		revolutPayment.setReference("ref");
		revolutPayment.setAmount(10);

		return revolutPayment;
	}

	public PaymentDTO buildPaymentDTO(RevolutPayment revolutPayment) {
		PaymentDTO paymentDTO = new PaymentDTO();

		paymentDTO.setSenderAccountId(revolutPayment.getAccountId());
		paymentDTO.setReceiverAccountId(revolutPayment.getReceiver().getAccountId());
		paymentDTO.setCounterpartyId(revolutPayment.getReceiver().getCounterPartyId());
		paymentDTO.setAmount(revolutPayment.getAmount());
		paymentDTO.setCurrency(revolutPayment.getCurrency());
		paymentDTO.setDescription(revolutPayment.getReference());

		return paymentDTO;
	}

	public TransactionError buildDanskeTransactionError() {
		return new TransactionError(Collections.singletonList("amount"));
	}

	public TransactionError buildRevolutTransactionError() {
		List<String> invalidKeys = Collections.singletonList("sender_account_id");
		return new TransactionError(invalidKeys);
	}

	public List<CommonAccount> buildCommonAccountList() {
		CommonAccount commonAccount = new CommonAccount();
		commonAccount.setBankName("danske");
		commonAccount.setAccountId("123");
		commonAccount.setCurrency("EUR");
		commonAccount.setBalance(10);

		return Collections.singletonList(commonAccount);
	}
}
