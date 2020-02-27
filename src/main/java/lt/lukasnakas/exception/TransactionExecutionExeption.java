package lt.lukasnakas.exception;

public class TransactionExecutionExeption extends RuntimeException {
    public TransactionExecutionExeption(String message) {
        super(message);
    }
}
