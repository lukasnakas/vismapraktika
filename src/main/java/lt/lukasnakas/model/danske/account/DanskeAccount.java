package lt.lukasnakas.model.danske.account;

import com.fasterxml.jackson.annotation.JsonAlias;
import lt.lukasnakas.model.Account;

public class DanskeAccount extends Account {

	@JsonAlias("Account")
	private DanskeAccountDetails[] account;

	public DanskeAccount() {
	}

	public DanskeAccount(String id, DanskeAccountDetails[] account) {
		super(id);
		this.account = account;
	}

	public DanskeAccountDetails[] getAccount() {
		return account;
	}

	public void setAccount(DanskeAccountDetails[] account) {
		this.account = account;
	}
}
