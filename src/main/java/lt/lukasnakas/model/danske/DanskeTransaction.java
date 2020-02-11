package lt.lukasnakas.model.danske;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Transaction;

public class DanskeTransaction extends Transaction {

	@JsonProperty("CreditDebitIndicator")
	private String creditDebitIndicator;

	@JsonProperty("Amount")
	private DanskeTransactionAmount transactionAmount;

	public DanskeTransaction() {
	}

	public DanskeTransaction(String id, String creditDebitIncidator, DanskeTransactionAmount transactionAmount) {
		super(id);
		this.creditDebitIndicator = creditDebitIncidator;
		this.transactionAmount = transactionAmount;
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
