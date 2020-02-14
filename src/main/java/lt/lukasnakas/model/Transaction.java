package lt.lukasnakas.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public abstract class Transaction {

	@JsonAlias("id")
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
