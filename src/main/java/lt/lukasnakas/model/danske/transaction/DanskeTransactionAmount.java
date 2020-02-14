package lt.lukasnakas.model.danske.transaction;

import com.fasterxml.jackson.annotation.JsonAlias;

public class DanskeTransactionAmount {

	@JsonAlias("Amount")
	private double amount;
	@JsonAlias("Currency")
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
