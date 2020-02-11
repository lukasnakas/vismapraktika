package lt.lukasnakas.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutTransactionLegs {

	@JsonProperty("leg_id")
	private String id;

	@JsonProperty("account_id")
	private String accountId;

	private double amount;
	private String currency;
	private String description;

	public RevolutTransactionLegs() {
	}

	public RevolutTransactionLegs(String id, String accountId, double amount, String currency, String description) {
		this.id = id;
		this.accountId = accountId;
		this.amount = amount;
		this.currency = currency;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
