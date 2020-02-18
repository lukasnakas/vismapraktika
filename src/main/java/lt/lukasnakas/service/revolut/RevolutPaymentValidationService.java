package lt.lukasnakas.service.revolut;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.service.PaymentValidationService;
import org.springframework.stereotype.Service;

@Service
public class RevolutPaymentValidationService implements PaymentValidationService {

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

}
