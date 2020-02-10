package lt.lukasnakas.model.danske;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DanskeTransaction {

	private String id;

	@JsonProperty("CreditDebitIndicator")
	private String creditDebitIndicator;

	@JsonProperty("Amount")
	private DanskeTransactionAmount transactionAmount;

	public DanskeTransaction() {
	}

	public DanskeTransaction(String id, String creditDebitIncidator, DanskeTransactionAmount transactionAmount) {
		this.id = id;
		this.creditDebitIndicator = creditDebitIncidator;
		this.transactionAmount = transactionAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
