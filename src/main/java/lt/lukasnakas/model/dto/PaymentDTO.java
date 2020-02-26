package lt.lukasnakas.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {

	@JsonIgnore
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
	@JsonProperty("bankName")
	private String bankName;
	@JsonProperty("paymentStatus")
	private String status;

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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(senderAccountId, receiverAccountId, counterpartyId, amount, description, currency, bankName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if(!(obj instanceof PaymentDTO)){
			return false;
		}

		PaymentDTO paymentDTO = (PaymentDTO) obj;

		return Objects.equals(senderAccountId, paymentDTO.senderAccountId) &&
				Objects.equals(receiverAccountId, paymentDTO.receiverAccountId) &&
				Objects.equals(counterpartyId, paymentDTO.counterpartyId) &&
				Objects.equals(amount, paymentDTO.amount) &&
				Objects.equals(description, paymentDTO.description) &&
				Objects.equals(currency, paymentDTO.currency) &&
				Objects.equals(bankName, paymentDTO.bankName);
	}
}
