package lt.lukasnakas.model.danske.transaction;

import com.fasterxml.jackson.annotation.JsonAlias;
import lt.lukasnakas.model.Transaction;

public class DanskeTransaction extends Transaction {

	@JsonAlias("AccountId")
	private String accountId;
	@JsonAlias("CreditDebitIndicator")
	private String creditDebitIndicator;
	@JsonAlias("Amount")
	private DanskeTransactionAmount transactionAmount;

	public DanskeTransaction() {
	}

	public DanskeTransaction(String id, String accountId, String creditDebitIndicator, DanskeTransactionAmount transactionAmount) {
		super(id);
		this.accountId = accountId;
		this.creditDebitIndicator = creditDebitIndicator;
		this.transactionAmount = transactionAmount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCreditDebitIndicator() {
		return creditDebitIndicator;
	}

	public void setCreditDebitIndicator(String creditDebitIndicator) {
		this.creditDebitIndicator = creditDebitIndicator;
	}

	public DanskeTransactionAmount getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(DanskeTransactionAmount transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
}
