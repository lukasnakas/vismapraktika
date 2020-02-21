package lt.lukasnakas.model.danske.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.danske.transaction.DanskeTransactionAmount;

public class Balance {

	@JsonProperty("AccountId")
	private String accountId;
	@JsonProperty("CreditDebitIndicator")
	private String creditDebitIndicator;
	@JsonProperty("Type")
	private String type;
	@JsonProperty("DateTime")
	private String datetime;
	@JsonProperty("Amount")
	private DanskeTransactionAmount amount;

	public Balance() {
	}

	public Balance(String accountId, String creditDebitIndicator, String type, String datetime,
				   DanskeTransactionAmount amount) {
		this.accountId = accountId;
		this.creditDebitIndicator = creditDebitIndicator;
		this.type = type;
		this.datetime = datetime;
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCreditDebitIndicator() {
		return creditDebitIndicator;
	}

	public void setCreditDebitIndicator(String creditDebitIndicator) {
		this.creditDebitIndicator = creditDebitIndicator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public DanskeTransactionAmount getAmount() {
		return amount;
	}

	public void setAmount(DanskeTransactionAmount amount) {
		this.amount = amount;
	}
}
