package lt.lukasnakas.service.danske;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.service.IPaymentValidationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DanskePaymentValidationService implements IPaymentValidationService {

    public boolean isValid(Payment payment) {
        return areParamsNotNull(payment);
    }

    private boolean areParamsNotNull(Payment payment) {
        return payment.getAmount() != 0;
    }

    public TransactionError getErrorWithMissingParamsFromPayment(Payment payment) {
        return new TransactionError(createMissingParamsList(payment));
    }

    private List<String> createMissingParamsList(Payment payment) {
        List<String> missingParamsList = new ArrayList<>();

        if (payment.getAmount() == 0) {
            missingParamsList.add("amount");
        }

        return missingParamsList;
    }

}
