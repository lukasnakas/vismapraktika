package lt.lukasnakas.service.revolut;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutTransfer;
import lt.lukasnakas.service.TransactionErrorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RevolutTransactionErrorService implements TransactionErrorService {

	public TransactionError getErrorWithAllMissingParamsFromPayment(Payment payment) {
		if (payment.getClass() == RevolutPayment.class) {
			RevolutPayment revolutPayment = (RevolutPayment) payment;
			return new TransactionError(createMissingParamsList(revolutPayment));
		} else {
			RevolutTransfer revolutTransfer = (RevolutTransfer) payment;
			return new TransactionError(createMissingParamsList(revolutTransfer));
		}
	}

	public List<String> createMissingParamsList(RevolutPayment revolutPayment) {
		List<String> missingParamsList = new ArrayList<>();

		if (revolutPayment.getType() == null) missingParamsList.add("type");
		if (revolutPayment.getAccountId() == null) missingParamsList.add("account_id");

		if (revolutPayment.getReceiver() == null) missingParamsList.add("receiver");
		else {
			if (revolutPayment.getReceiver().getAccountId() == null) missingParamsList.add("receiver.account_id");
			if (revolutPayment.getReceiver().getCounterPartyId() == null)
				missingParamsList.add("receiver.counterparty_id");
		}

		if (revolutPayment.getReference() == null) missingParamsList.add("reference");
		if (revolutPayment.getCurrency() == null) missingParamsList.add("currency");
		if (revolutPayment.getAmount() <= 0) missingParamsList.add("amount");

		return missingParamsList;
	}

	public List<String> createMissingParamsList(RevolutTransfer revolutTransfer) {
		List<String> missingParamsList = new ArrayList<>();

		if (revolutTransfer.getType() == null) missingParamsList.add("type");
		if (revolutTransfer.getDescription() == null) missingParamsList.add("description'");
		if (revolutTransfer.getTargetAccountId() == null) missingParamsList.add("target_account_id");
		if (revolutTransfer.getSourceAccountId() == null) missingParamsList.add("source_account_id");
		if (revolutTransfer.getCurrency() == null) missingParamsList.add("currency");
		if (revolutTransfer.getAmount() <= 0) missingParamsList.add("amount");

		return missingParamsList;
	}

}
