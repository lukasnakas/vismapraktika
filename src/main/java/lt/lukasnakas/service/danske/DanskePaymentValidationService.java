package lt.lukasnakas.service.danske;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.service.IPaymentValidationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DanskePaymentValidationService implements IPaymentValidationService {

    public boolean isValid(PaymentDTO paymentDTO) {
        return areParamsNotNull(paymentDTO);
    }

    private boolean areParamsNotNull(PaymentDTO paymentDTO) {
        return paymentDTO.getAmount() != 0;
    }

    public TransactionError getErrorWithMissingParamsFromPayment(PaymentDTO paymentDTO) {
        return new TransactionError(createMissingParamsList(paymentDTO));
    }

    private List<String> createMissingParamsList(PaymentDTO paymentDTO) {
        List<String> missingParamsList = new ArrayList<>();

        if (paymentDTO.getAmount() == 0) {
            missingParamsList.add("amount");
        }

        return missingParamsList;
    }

}
