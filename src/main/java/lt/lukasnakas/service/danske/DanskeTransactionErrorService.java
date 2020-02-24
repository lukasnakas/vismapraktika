package lt.lukasnakas.service.danske;

import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.service.TransactionErrorService;
import org.springframework.stereotype.Service;

@Service
public class DanskeTransactionErrorService implements TransactionErrorService {

    public TransactionError getErrorWithMissingParamsFromPayment(Payment payment) {
        return new TransactionError("amount");
    }

}
