package lt.lukasnakas.model.dto;

import java.util.Objects;

public class CommonTransactionDTO {

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

		if(!(obj instanceof CommonTransactionDTO)){
			return false;
		}

		CommonTransactionDTO commonTransactionDTO = (CommonTransactionDTO) obj;

		return Objects.equals(id, commonTransactionDTO.id) &&
				Objects.equals(senderAccountId, commonTransactionDTO.senderAccountId) &&
				Objects.equals(receiverAccountId, commonTransactionDTO.receiverAccountId) &&
				Objects.equals(amount, commonTransactionDTO.amount) &&
				Objects.equals(currency, commonTransactionDTO.currency);
	}
}
