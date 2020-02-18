package lt.lukasnakas.exception;

import java.util.function.Supplier;

public class AccountRetrievalException extends RuntimeException {

    public AccountRetrievalException(String message) {
        super(message);
    }
}
