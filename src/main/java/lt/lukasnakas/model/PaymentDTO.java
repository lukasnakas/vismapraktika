package lt.lukasnakas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentDTO {

	private long id;
	@JsonProperty("sender_account_id")
	private String senderAccountId;
	@JsonProperty("receiver_account_id")
	private String receiverAccountId;
	@JsonProperty("counterparty_id")
	private String counterpartyId;
	@JsonProperty("amount")
	private double amount;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("description")
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSenderAccountId() {
		return senderAccountId;
	}

	public void setSenderAccountId(String senderAccountId) {
		this.senderAccountId = senderAccountId;
	}

	public String getReceiverAccountId() {
		return receiverAccountId;
	}

	public void setReceiverAccountId(String receiverAccountId) {
		this.receiverAccountId = receiverAccountId;
	}

	public String getCounterpartyId() {
		return counterpartyId;
	}

	public void setCounterpartyId(String counterpartyId) {
		this.counterpartyId = counterpartyId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "PaymentDTO{" +
				"senderAccountId='" + senderAccountId + '\'' +
				", receiverAccountId='" + receiverAccountId + '\'' +
				", counterpartyId='" + counterpartyId + '\'' +
				", amount=" + amount +
				", currency='" + currency + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
