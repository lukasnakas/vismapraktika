package lt.lukasnakas.model.revolut.transaction;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutTransaction extends RevolutTransactionBase {

	private String type;

	@JsonProperty("request_id")
	private String requestId;

	@JsonProperty("updated_at")
	private String updatedAt;
	private String reference;
	private RevolutTransactionLegs[] legs;

	public RevolutTransaction() {
	}

	public RevolutTransaction(String id, String state, String createdAt, String completedAt, String type,
							  String requestId, String updatedAt, String reference, RevolutTransactionLegs[] legs) {
		super(id, state, createdAt, completedAt);
		this.type = type;
		this.requestId = requestId;
		this.updatedAt = updatedAt;
		this.reference = reference;
		this.legs = legs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public RevolutTransactionLegs[] getLegs() {
		return legs;
	}

	public void setLegs(RevolutTransactionLegs[] legs) {
		this.legs = legs;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
