package lt.lukasnakas.model;

public enum PaymentStatus {
	IN_QUEUE("in_queue"),
	COMPLETED("completed"),
	FAILED("failed");

	private final String value;

	PaymentStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
