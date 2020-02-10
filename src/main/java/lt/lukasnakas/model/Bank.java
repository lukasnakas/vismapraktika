package lt.lukasnakas.model;

public enum Bank {
	DANSKE,
	REVOLUT;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
