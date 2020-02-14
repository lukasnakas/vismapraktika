package lt.lukasnakas.service.danske;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.service.TransactionErrorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DanskeTransactionErrorService implements TransactionErrorService {

	public TransactionError getErrorWithMissingParamsFromPayment(Payment payment) {
		return new TransactionError("amount");
	}

}
