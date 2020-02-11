package lt.lukasnakas.model.danske;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DanskeTransactionAmount {

	@JsonProperty("Amount")
	private double amount;

	@JsonProperty("Currency")
	private String currency;

	public DanskeTransactionAmount() {
	}

	public DanskeTransactionAmount(double amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
