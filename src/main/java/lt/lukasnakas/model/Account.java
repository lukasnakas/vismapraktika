package lt.lukasnakas.model;

public abstract class Account {

	private String id;

	public Account() {
	}

	public Account(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
