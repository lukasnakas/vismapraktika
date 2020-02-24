package lt.lukasnakas.util;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.danske.account.Balance;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.account.Data;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.danske.transaction.DanskeTransactionAmount;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

public class MockedDataGenerator {

	public ResponseEntity<DanskeAccount> getMockedDanskeAccountResponseEntity() {
		Balance[] balances = new Balance[1];
		balances[0] = new Balance("123", "debit", "type", "2020",
				new DanskeTransactionAmount(55, "EUR"));
		DanskeAccount danskeAccount = new DanskeAccount("456", new Data(balances));
		return new ResponseEntity<>(danskeAccount, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<List<RevolutAccount>> getMockedRevolutAccountResponseEntity() {
		List<RevolutAccount> revolutAccountList = Collections.singletonList(new RevolutAccount("123",
				"name", 10, "GBP","state", true,
				"2002", "2002"));
		return new ResponseEntity<>(revolutAccountList, HttpStatus.ACCEPTED);
	}

	private RevolutTransaction buildRevolutTransaction() {
		RevolutCounterparty revolutCounterparty = new RevolutCounterparty("type", "123");
		RevolutTransactionLegs[] revolutTransactionLegs = new RevolutTransactionLegs[1];
		revolutTransactionLegs[0] = new RevolutTransactionLegs("123", "456", revolutCounterparty,
				10, "GBP", "desc", 100);
		return new RevolutTransaction("123", "type", "456", "2020", "ref",
				revolutTransactionLegs, "active", "2020", "2020");
	}

	private DanskeTransaction buildDanskeTransaction() {
		return new DanskeTransaction("123", "456", "debit",
				new DanskeTransactionAmount(5, "EUR"));
	}

	public CommonAccount buildCommonAccount(DanskeAccount danskeAccount) {
		return new CommonAccount("Danske",
				danskeAccount.getData().getBalance()[0].getAccountId(),
				danskeAccount.getData().getBalance()[0].getAmount().getAmount(),
				danskeAccount.getData().getBalance()[0].getAmount().getCurrency());
	}

	public CommonAccount buildCommonAccount(RevolutAccount revolutAccount) {
		return new CommonAccount("Danske",
				revolutAccount.getId(),
				revolutAccount.getBalance(),
				revolutAccount.getCurrency());
	}

	public ResponseEntity<List<DanskeTransaction>> getMockedDanskeTransactionResponseEntity() {
		List<DanskeTransaction> danskeTransactionList = Collections.singletonList(buildDanskeTransaction());
		return new ResponseEntity<>(danskeTransactionList, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<List<RevolutTransaction>> getMockedRevolutTransactionResponseEntity() {
		List<RevolutTransaction> revolutTransactionList = Collections.singletonList(buildRevolutTransaction());
		return new ResponseEntity<>(revolutTransactionList, HttpStatus.ACCEPTED);
	}

	public CommonTransaction buildCommonTransaction(DanskeTransaction danskeTransaction) {
		return new CommonTransaction(danskeTransaction.getId(),
				danskeTransaction.getAccountId(),
				null,
				danskeTransaction.getTransactionAmount().getAmount(),
				danskeTransaction.getTransactionAmount().getCurrency());
	}

	public CommonTransaction buildCommonTransaction(RevolutTransaction revolutTransaction, Payment payment) {
		return new CommonTransaction(revolutTransaction.getId(),
				payment.getSenderAccountId(),
				payment.getReceiverAccountId(),
				payment.getAmount(),
				payment.getCurrency());
	}

	public CommonTransaction buildCommonTransaction() {
		return new CommonTransaction("123", "456", "789",
				10, "EUR");
	}

	public ResponseEntity<DanskeTransaction> getMockedDanskeTransactionResponseEntityForPost() {
		return new ResponseEntity<>(buildDanskeTransaction(), HttpStatus.ACCEPTED);
	}

	public ResponseEntity<RevolutTransaction> getMockedRevolutTransactionResponseEntityForPost() {
		return new ResponseEntity<>(buildRevolutTransaction(), HttpStatus.ACCEPTED);
	}

	public Payment buildDanskeTransactionPayment() {
		return new Payment("123", "456",
				"789", 100, "EUR", "desc");
	}

	public Payment buildRevolutTransactionPayment() {
		return new Payment("123", "789", "456",
				789, "GBP", "desc");
	}

	public TransactionError buildDanskeTransactionError() {
		return new TransactionError("amount");
	}

	public TransactionError buildRevolutTransactionError() {
		return new TransactionError("sender_account_id");
	}
}
