package lt.lukasnakas.exception;

public class TransactionRetrievalException extends RuntimeException {
    public TransactionRetrievalException(String message) {
        super(message);
    }
}
