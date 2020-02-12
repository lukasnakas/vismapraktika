package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutTransfer extends RevolutPaymentTransferBase {

	@JsonProperty("source_account_id")
	private String sourceAccountId;

	@JsonProperty("target_account_id")
	private String targetAccountId;
	private String description;

	public RevolutTransfer() {
	}

	public RevolutTransfer(String bankName, String requestId, double amount, String currency,
						   String sourceAccountId, String targetAccountId, String description, String type) {
		super(bankName, requestId, amount, currency, type);
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.description = description;
	}

	public String getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(String sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public String getTargetAccountId() {
		return targetAccountId;
	}

	public void setTargetAccountId(String targetAccountId) {
		this.targetAccountId = targetAccountId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "RevolutTransfer{" +
				"request_id='" + getRequestId() + '\'' +
				", sourceAccountId='" + sourceAccountId + '\'' +
				", targetAccountId='" + targetAccountId + '\'' +
				", amount='" + getAmount() + '\'' +
				", currency='" + getCurrency() + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
