package lt.lukasnakas.service.danske;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.danske.transaction.DanskePayment;
import lt.lukasnakas.service.TransactionErrorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DanskeTransactionErrorService implements TransactionErrorService {

	public TransactionError getErrorWithAllMissingParamsFromPayment(Payment payment){
		DanskePayment danskePayment = (DanskePayment) payment;
		return new TransactionError(createMissingParamsList(danskePayment));
	}

	private List<String> createMissingParamsList(DanskePayment danskePayment){
		List<String> missingParamsList = new ArrayList<>();

		if (danskePayment.getTemplate() == null) missingParamsList.add("template");
		if (danskePayment.getAmount() == 0) missingParamsList.add("amount");

		return missingParamsList;
	}

}
