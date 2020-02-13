package lt.lukasnakas.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lt.lukasnakas.model.Transaction;

import java.util.List;

@JsonIgnoreProperties({"id"})
public class TransactionError extends Transaction {

	private String message;

	public TransactionError() {
	}

	public TransactionError(String invalidKey) {
		this.message = "Invalid or missing param: " + invalidKey;
	}

	public TransactionError(List<String> invalidKeys){
		this.message = "Invalid or missing params: " + invalidKeys;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "TransactionError{" +
				"message='" + message + '\'' +
				'}';
	}
}
