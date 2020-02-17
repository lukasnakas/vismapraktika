package lt.lukasnakas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Transaction {

	@JsonProperty("id")
	private String id;

	public Transaction() {
	}

	public Transaction(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
