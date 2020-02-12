package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutPayment extends RevolutPaymentTransferBase {

	@JsonProperty("account_id")
	private String accountId;
	private RevolutReceiver receiver;
	private String reference;

	public RevolutPayment() {
	}

	public RevolutPayment(String bankName, String requestId, double amount, String currency,
						  String accountId, RevolutReceiver receiver, String reference, String type) {
		super(bankName, requestId, amount, currency, type);
		this.accountId = accountId;
		this.receiver = receiver;
		this.reference = reference;
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
