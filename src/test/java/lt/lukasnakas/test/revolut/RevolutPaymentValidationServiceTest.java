package lt.lukasnakas.test.revolut;

import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutReceiver;
import lt.lukasnakas.service.revolut.RevolutPaymentValidationService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RevolutPaymentValidationServiceTest {

	@InjectMocks
	private RevolutPaymentValidationService revolutPaymentValidationService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void getErrorWithMissingParamsFromPayment_shouldReturnTrue_when() {
		RevolutPayment revolutPayment = testDataGenerator.buildInvalidRevolutTransactionPayment();

		TransactionError expected = testDataGenerator.buildRevolutTransactionError();
		TransactionError actual = revolutPaymentValidationService.getErrorWithMissingParamsFromPayment(testDataGenerator.buildPaymentDTO(revolutPayment));

		assertEquals(expected, actual);
	}

	@Test
	public void isValid_shouldReturnTrue() {
		RevolutReceiver revolutReceiver = new RevolutReceiver();
		revolutReceiver.setAccountId("123");
		revolutReceiver.setCounterPartyId("456");

		RevolutPayment revolutPayment = new RevolutPayment();
		revolutPayment.setAccountId("123");
		revolutPayment.setReceiver(revolutReceiver);
		revolutPayment.setCurrency("GBP");
		revolutPayment.setReference("ref");
		revolutPayment.setAmount(10);

		boolean actual = revolutPaymentValidationService.isValid(testDataGenerator.buildPaymentDTO(revolutPayment));
		assertTrue(actual);
	}

	@Test
	public void isValidNoAmount_shouldReturnFalse() {
		RevolutReceiver revolutReceiver = new RevolutReceiver();
		revolutReceiver.setAccountId("123");
		revolutReceiver.setCounterPartyId("456");

		RevolutPayment revolutPayment = new RevolutPayment();
		revolutPayment.setAccountId("123");
		revolutPayment.setReceiver(revolutReceiver);
		revolutPayment.setCurrency("GBP");
		revolutPayment.setReference("ref");
		revolutPayment.setAmount(0);

		boolean actual = revolutPaymentValidationService.isValid(testDataGenerator.buildPaymentDTO(revolutPayment));
		assertFalse(actual);
	}

	@Test
	public void isValidNegativeAmount_shouldReturnFalse() {
		RevolutReceiver revolutReceiver = new RevolutReceiver();
		revolutReceiver.setAccountId("123");
		revolutReceiver.setCounterPartyId("456");

		RevolutPayment revolutPayment = new RevolutPayment();
		revolutPayment.setAccountId("123");
		revolutPayment.setReceiver(revolutReceiver);
		revolutPayment.setCurrency("GBP");
		revolutPayment.setReference("ref");
		revolutPayment.setAmount(-10);

		boolean actual = revolutPaymentValidationService.isValid(testDataGenerator.buildPaymentDTO(revolutPayment));
		assertFalse(actual);
	}

	@Test
	public void isValidNoCurrency_shouldReturnFalse() {
		RevolutReceiver revolutReceiver = new RevolutReceiver();
		revolutReceiver.setAccountId("123");
		revolutReceiver.setCounterPartyId("456");

		RevolutPayment revolutPayment = new RevolutPayment();
		revolutPayment.setAccountId("123");
		revolutPayment.setReceiver(revolutReceiver);
		revolutPayment.setCurrency(null);
		revolutPayment.setReference("ref");
		revolutPayment.setAmount(10);

		boolean actual = revolutPaymentValidationService.isValid(testDataGenerator.buildPaymentDTO(revolutPayment));
		assertFalse(actual);
	}

	@Test
	public void isValidNoAccountId_shouldReturnFalse() {
		RevolutReceiver revolutReceiver = new RevolutReceiver();
		revolutReceiver.setAccountId("123");
		revolutReceiver.setCounterPartyId("456");

		RevolutPayment revolutPayment = new RevolutPayment();
		revolutPayment.setAccountId(null);
		revolutPayment.setReceiver(revolutReceiver);
		revolutPayment.setCurrency("GBP");
		revolutPayment.setReference("ref");
		revolutPayment.setAmount(10);

		boolean actual = revolutPaymentValidationService.isValid(testDataGenerator.buildPaymentDTO(revolutPayment));
		assertFalse(actual);
	}

	@Test
	public void isValidNoRevolutReceiver_shouldReturnFalse() {
		RevolutPayment revolutPayment = new RevolutPayment();
		revolutPayment.setAccountId("123");
		revolutPayment.setReceiver(null);
		revolutPayment.setCurrency("GBP");
		revolutPayment.setReference("ref");
		revolutPayment.setAmount(10);

		boolean actual = revolutPaymentValidationService.isValid(testDataGenerator.buildPaymentDTO(revolutPayment));
		assertFalse(actual);
	}

	@Test
	public void isValidNoRevolutReceiverCounterpartyId_shouldReturnFalse() {
		RevolutReceiver revolutReceiver = new RevolutReceiver();
		revolutReceiver.setAccountId("123");
		revolutReceiver.setCounterPartyId(null);

		RevolutPayment revolutPayment = new RevolutPayment();
		revolutPayment.setAccountId("123");
		revolutPayment.setReceiver(revolutReceiver);
		revolutPayment.setCurrency("GBP");
		revolutPayment.setReference("ref");
		revolutPayment.setAmount(10);

		boolean actual = revolutPaymentValidationService.isValid(testDataGenerator.buildPaymentDTO(revolutPayment));
		assertFalse(actual);
	}

	@Test
	public void isValidNoRevolutReceiverAccountId_shouldReturnFalse() {
		RevolutReceiver revolutReceiver = new RevolutReceiver();
		revolutReceiver.setAccountId(null);
		revolutReceiver.setCounterPartyId("456");

		RevolutPayment revolutPayment = new RevolutPayment();
		revolutPayment.setAccountId("123");
		revolutPayment.setReceiver(revolutReceiver);
		revolutPayment.setCurrency("GBP");
		revolutPayment.setReference("ref");
		revolutPayment.setAmount(10);

		boolean actual = revolutPaymentValidationService.isValid(testDataGenerator.buildPaymentDTO(revolutPayment));
		assertFalse(actual);
	}

	@Test
	public void isValidNoReference_shouldReturnFalse() {
		RevolutReceiver revolutReceiver = new RevolutReceiver();
		revolutReceiver.setAccountId("123");
		revolutReceiver.setCounterPartyId("456");

		RevolutPayment revolutPayment = new RevolutPayment();
		revolutPayment.setAccountId("123");
		revolutPayment.setReceiver(revolutReceiver);
		revolutPayment.setCurrency("GBP");
		revolutPayment.setReference(null);
		revolutPayment.setAmount(10);

		boolean actual = revolutPaymentValidationService.isValid(testDataGenerator.buildPaymentDTO(revolutPayment));
		assertFalse(actual);
	}

}
