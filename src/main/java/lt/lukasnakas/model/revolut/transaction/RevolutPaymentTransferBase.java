package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Payment;

public abstract class RevolutPaymentTransferBase extends Payment {

	@JsonProperty("request_id")
	private String requestId;
	private double amount;
	private String currency;
	private String type;

	public RevolutPaymentTransferBase() {
	}

	public RevolutPaymentTransferBase(String bankName, String requestId, double amount, String currency, String type) {
		super(bankName);
		this.requestId = requestId;
		this.amount = amount;
		this.currency = currency;
		this.type = type;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
