package lt.lukasnakas.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.Transaction;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties({"id"})
public class TransactionError extends Transaction {

    private String message;

    public TransactionError() {
    }

    public TransactionError(String invalidKey) {
        this.message = "Invalid or missing param: " + invalidKey;
    }

    public TransactionError(List<String> invalidKeys) {
        this.message = "Invalid or missing params: " + invalidKeys;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if(!(obj instanceof TransactionError)){
            return false;
        }

        TransactionError transactionError = (TransactionError) obj;

        return Objects.equals(message, transactionError.message);
    }

    @Override
    public String toString() {
        return "TransactionError{" +
                "message='" + message + '\'' +
                '}';
    }
}
