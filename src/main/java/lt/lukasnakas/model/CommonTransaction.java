package lt.lukasnakas.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class CommonTransaction {

	@Id
	private String id;
	private String senderAccountId;
	private String receiverAccountId;
	private double amount;
	private String currency;

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
	public int hashCode() {
		return Objects.hash(id, senderAccountId, receiverAccountId, amount, currency);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if(!(obj instanceof CommonTransaction)){
			return false;
		}

		CommonTransaction commonTransaction = (CommonTransaction) obj;

		return Objects.equals(id, commonTransaction.id) &&
				Objects.equals(senderAccountId, commonTransaction.senderAccountId) &&
				Objects.equals(receiverAccountId, commonTransaction.receiverAccountId) &&
				Objects.equals(amount, commonTransaction.amount) &&
				Objects.equals(currency, commonTransaction.currency);
	}
}
