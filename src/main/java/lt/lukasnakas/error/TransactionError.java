package lt.lukasnakas.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lt.lukasnakas.model.Transaction;

@JsonIgnoreProperties({"id"})
public class TransactionError extends Transaction {

	private String message;

	public TransactionError() {
	}

	public TransactionError(String invalidKey) {
		this.message = "parameter '" + invalidKey + "' is invalid or missing";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
