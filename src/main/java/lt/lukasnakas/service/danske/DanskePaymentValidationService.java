package lt.lukasnakas.service.danske;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.service.PaymentValidationService;
import org.springframework.stereotype.Service;

@Service
public class DanskePaymentValidationService implements PaymentValidationService {

	public boolean isValid(Payment payment) {
		return areParamsNotNull(payment);
	}

	private boolean areParamsNotNull(Payment payment) {
		return payment.getAmount() != 0;
	}

}
