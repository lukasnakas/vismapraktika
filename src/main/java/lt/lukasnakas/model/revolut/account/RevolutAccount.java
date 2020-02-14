package lt.lukasnakas.model.revolut.account;

import com.fasterxml.jackson.annotation.JsonAlias;
import lt.lukasnakas.model.Account;

public class RevolutAccount extends Account {

	@JsonAlias("name")
	private String name;
	@JsonAlias("balance")
	private double balance;
	@JsonAlias("currency")
	private String currency;
	@JsonAlias("state")
	private String state;
	@JsonAlias("publicAccount")
	private boolean publicAccount;
	@JsonAlias("created_at")
	private String createdAt;
	@JsonAlias("updated_at")
	private String updatedAt;

	public RevolutAccount() {
	}

	public RevolutAccount(String id, String name, double balance, String currency, String state,
						  boolean publicAccount, String createdAt, String updatedAt) {
		super(id);
		this.name = name;
		this.balance = balance;
		this.currency = currency;
		this.state = state;
		this.publicAccount = publicAccount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isPublicAccount() {
		return publicAccount;
	}

	public void setPublicAccount(boolean publicAccount) {
		this.publicAccount = publicAccount;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
