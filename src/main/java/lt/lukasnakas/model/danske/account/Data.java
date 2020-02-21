package lt.lukasnakas.model.danske.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Data {

	@JsonProperty("Balance")
	private Balance[] balance;

	public Data() {
	}

	public Data(Balance[] balance) {
		this.balance = balance;
	}

	public Balance[] getBalance() {
		return balance;
	}

	public void setBalance(Balance[] balance) {
		this.balance = balance;
	}
}
