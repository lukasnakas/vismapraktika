package lt.lukasnakas.model.danske.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	@JsonProperty("Account")
	private DanskeAccount[] account;

	public Data() {
	}

	public Data(DanskeAccount[] account) {
		this.account = account;
	}

	public DanskeAccount[] getAccount() {
		return account;
	}

	public void setAccount(DanskeAccount[] account) {
		this.account = account;
	}
}
