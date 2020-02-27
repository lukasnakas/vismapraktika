package lt.lukasnakas.exception;

public class BankTypeNotSupportedException extends RuntimeException {
	public BankTypeNotSupportedException(String message) {
		super(message);
	}
}
