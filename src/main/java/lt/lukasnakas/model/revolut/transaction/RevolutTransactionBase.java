package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Transaction;

public class RevolutTransactionBase extends Transaction {

	private String state;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("completed_at")
	private String completedAt;

	public RevolutTransactionBase() {
	}

	public RevolutTransactionBase(String id, String state, String createdAt, String completedAt) {
		super(id);
		this.state = state;
		this.createdAt = createdAt;
		this.completedAt = completedAt;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}
}
