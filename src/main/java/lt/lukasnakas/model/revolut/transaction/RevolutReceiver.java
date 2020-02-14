package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonAlias;

public class RevolutReceiver {

	@JsonAlias("counterparty_id")
	private String counterPartyId;
	@JsonAlias("account_id")
	private String accountId;

	public RevolutReceiver() {
	}

	public RevolutReceiver(String counterPartyId, String accountId) {
		this.counterPartyId = counterPartyId;
		this.accountId = accountId;
	}

	public String getCounterPartyId() {
		return counterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
