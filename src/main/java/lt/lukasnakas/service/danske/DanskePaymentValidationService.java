package lt.lukasnakas.service.danske;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.danske.transaction.DanskePayment;
import lt.lukasnakas.service.PaymentValidationService;
import org.springframework.stereotype.Service;

@Service
public class DanskePaymentValidationService implements PaymentValidationService {

	public boolean isValid(Payment payment){
		DanskePayment danskePayment = (DanskePayment) payment;
		return areParamsNotNull(danskePayment);
	}

	private boolean areParamsNotNull(DanskePayment danskePayment){
		return danskePayment.getTemplate() != null &&
				danskePayment.getAmount() != 0;
	}

}
