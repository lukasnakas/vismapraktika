package lt.lukasnakas.model.danske.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

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

	@Override
	public String toString() {
		return "Data{" +
				"balance=" + Arrays.toString(balance) +
				'}';
	}
}
