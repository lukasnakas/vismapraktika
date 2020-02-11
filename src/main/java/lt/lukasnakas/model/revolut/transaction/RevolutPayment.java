package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Payment;

public class RevolutPayment extends Payment {

	@JsonProperty("request_id")
	private String requestId;

	@JsonProperty("account_id")
	private String accountId;

	private RevolutReceiver receiver;
	private double amount;
	private String currency;
	private String reference;

	public RevolutPayment() {
	}

	public RevolutPayment(String bankName, String requestId, String accountId, RevolutReceiver receiver,
						  double amount, String currency, String reference) {
		super(bankName);
		this.requestId = requestId;
		this.accountId = accountId;
		this.receiver = receiver;
		this.amount = amount;
		this.currency = currency;
		this.reference = reference;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public RevolutReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(RevolutReceiver receiver) {
		this.receiver = receiver;
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
