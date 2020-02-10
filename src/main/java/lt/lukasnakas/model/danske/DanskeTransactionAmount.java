package lt.lukasnakas.model.danske;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DanskeTransactionAmount {

	@JsonProperty("Amount")
	private long amount;

	@JsonProperty("Currency")
	private String currency;

	public DanskeTransactionAmount() {
	}

	public DanskeTransactionAmount(long amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
