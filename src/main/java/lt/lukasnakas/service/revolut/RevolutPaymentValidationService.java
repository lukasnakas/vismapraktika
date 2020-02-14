package lt.lukasnakas.service.revolut;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.service.PaymentValidationService;
import org.springframework.stereotype.Service;

@Service
public class RevolutPaymentValidationService implements PaymentValidationService {

	public boolean isValid(Payment payment) {
		return areRevolutPaymentParamsNotNull(payment);
	}

	private boolean areRevolutPaymentParamsNotNull(Payment payment) {
		return payment.getSenderAccountId() != null
				&& payment.getReceiverAccountId() != null
				&& payment.getDescription() != null
				&& payment.getCurrency() != null
				&& payment.getAmount() > 0;
	}

}
