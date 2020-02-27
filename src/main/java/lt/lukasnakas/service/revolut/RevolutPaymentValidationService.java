package lt.lukasnakas.service.revolut;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.service.IPaymentValidationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RevolutPaymentValidationService implements IPaymentValidationService {

    public boolean isValid(Payment payment) {
        return areRevolutPaymentParamsNotNull((RevolutPayment) payment);
    }

    private boolean areRevolutPaymentParamsNotNull(RevolutPayment revolutPayment) {
        return revolutPayment.getAccountId() != null
                && revolutPayment.getReceiver() != null
                && revolutPayment.getReceiver().getAccountId() != null
                && revolutPayment.getReceiver().getCounterPartyId() != null
                && revolutPayment.getReference() != null
                && revolutPayment.getCurrency() != null
                && revolutPayment.getAmount() > 0;
    }

    public TransactionError getErrorWithMissingParamsFromPayment(Payment payment) {
        RevolutPayment revolutPayment = (RevolutPayment) payment;
        return new TransactionError(createMissingParamsList(revolutPayment));
    }

    private List<String> createMissingParamsList(RevolutPayment revolutPayment) {
        List<String> missingParamsList = new ArrayList<>();

        if (revolutPayment.getAccountId() == null) {
            missingParamsList.add("sender_account_id");
        }
        if (revolutPayment.getReceiver() == null) {
            missingParamsList.add("receiver");
        }
        if (revolutPayment.getReceiver().getAccountId() == null) {
            missingParamsList.add("receiver_account_id");
        }
        if (revolutPayment.getReceiver().getCounterPartyId() == null) {
            missingParamsList.add("counterparty_id");
        }
        if (revolutPayment.getReference() == null) {
            missingParamsList.add("description");
        }
        if (revolutPayment.getCurrency() == null) {
            missingParamsList.add("currency");
        }
        if (revolutPayment.getAmount() <= 0) {
            missingParamsList.add("amount");
        }

        return missingParamsList;
    }

}
