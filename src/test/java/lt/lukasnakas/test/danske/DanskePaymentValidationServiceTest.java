package lt.lukasnakas.test.danske;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.service.danske.DanskePaymentValidationService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DanskePaymentValidationServiceTest {

	@InjectMocks
	private DanskePaymentValidationService danskePaymentValidationService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void getErrorWithMissingParamsFromPayment_shouldReturnTrue_whenErrorsMatch() {
		Payment payment = testDataGenerator.buildInvalidTransactionPayment();

		TransactionError expected = testDataGenerator.buildDanskeTransactionError();
		TransactionError actual = danskePaymentValidationService.getErrorWithMissingParamsFromPayment(payment);

		assertEquals(expected, actual);
	}

	@Test
	public void isValid_shouldReturnTrue() {
		Payment payment = new Payment();
		payment.setSenderAccountId(null);
		payment.setReceiverAccountId(null);
		payment.setCounterpartyId(null);
		payment.setDescription(null);
		payment.setCurrency(null);
		payment.setAmount(100);

		boolean actual = danskePaymentValidationService.isValid(payment);
		assertTrue(actual);
	}

	@Test
	public void isValidNoAmount_shouldReturnFalse() {
		Payment payment = new Payment();
		payment.setSenderAccountId(null);
		payment.setReceiverAccountId(null);
		payment.setCounterpartyId(null);
		payment.setDescription(null);
		payment.setCurrency(null);
		payment.setAmount(0);

		boolean actual = danskePaymentValidationService.isValid(payment);
		assertFalse(actual);
	}

}
