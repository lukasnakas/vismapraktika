package lt.lukasnakas.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CommonTransaction {

	@Id
	private String id;
	private String senderAccountId;
	private String receiverAccountId;
	private double amount;
	private String currency;

	public CommonTransaction() {
	}

	public CommonTransaction(String id, String senderAccountId, String receiverAccountId, double amount, String currency) {
		this.id = id;
		this.senderAccountId = senderAccountId;
		this.receiverAccountId = receiverAccountId;
		this.amount = amount;
		this.currency = currency;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	@Override
	public String toString() {
		return "CommonTransaction{" +
				"senderAccountId='" + senderAccountId + '\'' +
				", receiverAccountId='" + receiverAccountId + '\'' +
				", amount=" + amount +
				", currency='" + currency + '\'' +
				'}';
	}
}