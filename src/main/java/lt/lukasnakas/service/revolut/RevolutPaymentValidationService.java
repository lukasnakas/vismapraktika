package lt.lukasnakas.service.revolut;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutReceiver;
import lt.lukasnakas.model.revolut.transaction.RevolutTransfer;
import lt.lukasnakas.service.PaymentValidationService;
import org.springframework.stereotype.Service;

@Service
public class RevolutPaymentValidationService implements PaymentValidationService {

	public boolean isValid(Payment payment) {
		if (payment.getClass() == RevolutPayment.class) {
			RevolutPayment revolutPayment = (RevolutPayment) payment;
			return areRevolutPaymentParamsNotNull(revolutPayment);
		} else {
			RevolutTransfer revolutTransfer = (RevolutTransfer) payment;
			return areRevolutTransferParamsNotNull(revolutTransfer);
		}
	}

	private boolean areRevolutPaymentParamsNotNull(RevolutPayment revolutPayment) {
		return revolutPayment.getRequestId() == null
				&& revolutPayment.getAccountId() == null
				&& areRevolutReceiverParamsNotNull(revolutPayment.getReceiver())
				&& revolutPayment.getReference() == null
				&& revolutPayment.getCurrency() == null
				&& revolutPayment.getAmount() <= 0;
	}

	private boolean areRevolutReceiverParamsNotNull(RevolutReceiver revolutReceiver) {
		return revolutReceiver != null && revolutReceiver.getAccountId() != null
				&& revolutReceiver.getCounterPartyId() != null;
	}

	private boolean areRevolutTransferParamsNotNull(RevolutTransfer revolutTransfer) {
		return revolutTransfer.getRequestId() == null
				&& revolutTransfer.getSourceAccountId() == null
				&& revolutTransfer.getTargetAccountId() == null
				&& revolutTransfer.getDescription() == null
				&& revolutTransfer.getCurrency() == null
				&& revolutTransfer.getAmount() <= 0;
	}

}
