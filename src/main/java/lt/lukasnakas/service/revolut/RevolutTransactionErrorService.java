package lt.lukasnakas.service.revolut;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.service.TransactionErrorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RevolutTransactionErrorService implements TransactionErrorService {

	public TransactionError getErrorWithMissingParamsFromPayment(Payment payment) {
		return new TransactionError(createMissingParamsList(payment));
	}

	public List<String> createMissingParamsList(Payment payment) {
		List<String> missingParamsList = new ArrayList<>();

		if (payment.getSenderAccountId() == null) missingParamsList.add("account_id");
		if (payment.getReceiverAccountId() == null) missingParamsList.add("receiver");
		if (payment.getDescription() == null) missingParamsList.add("reference");
		if (payment.getCurrency() == null) missingParamsList.add("currency");
		if (payment.getAmount() <= 0) missingParamsList.add("amount");

		return missingParamsList;
	}

}
