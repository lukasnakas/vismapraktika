package lt.lukasnakas;

import lt.lukasnakas.model.danske.transaction.DanskePayment;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutReceiver;
import lt.lukasnakas.service.revolut.RevolutPaymentValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RevolutPaymentValidationServiceTest {

	@InjectMocks
	private RevolutPaymentValidationService revolutPaymentValidationService;

	@Test
	public void isValid_shouldReturnTrue(){
		RevolutPayment payment = new RevolutPayment("revolut", "123", 100, "EUR",
				"456", new RevolutReceiver("789", "123456"), "ref", "payment");
		boolean actual = revolutPaymentValidationService.isValid(payment);
		assertEquals(true, actual);
	}

	@Test
	public void isValidNoAmount_shouldReturnFalse(){
		RevolutPayment payment = new RevolutPayment("revolut", "123", 0, "EUR",
				"456", new RevolutReceiver("789", "123456"), "ref", "payment");
		boolean actual = revolutPaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

	@Test
	public void isValidNegativeAmount_shouldReturnFalse(){
		RevolutPayment payment = new RevolutPayment("revolut", "123", -10, "EUR",
				"456", new RevolutReceiver("789", "123456"), "ref", "payment");
		boolean actual = revolutPaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

	@Test
	public void isValidNoCurrency_shouldReturnFalse(){
		RevolutPayment payment = new RevolutPayment("revolut", "123", 0, null,
				"456", new RevolutReceiver("789", "123456"), "ref", "payment");
		boolean actual = revolutPaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

	@Test
	public void isValidNoAccountId_shouldReturnFalse(){
		RevolutPayment payment = new RevolutPayment("revolut", "123", 0, "EUR",
				null, new RevolutReceiver("789", "123456"), "ref", "payment");
		boolean actual = revolutPaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

	@Test
	public void isValidNoRevolutReceiver_shouldReturnFalse(){
		RevolutPayment payment = new RevolutPayment("revolut", "123", 0, "EUR",
				"456", null, "ref", "payment");
		boolean actual = revolutPaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

	@Test
	public void isValidNoRevolutReceiverCounterpartyId_shouldReturnFalse(){
		RevolutPayment payment = new RevolutPayment("revolut", "123", 0, "EUR",
				"456", new RevolutReceiver(null, "123456"), "ref", "payment");
		boolean actual = revolutPaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

	@Test
	public void isValidNoRevolutReceiverAccountId_shouldReturnFalse(){
		RevolutPayment payment = new RevolutPayment("revolut", "123", 0, "EUR",
				"456", new RevolutReceiver("789", null), "ref", "payment");
		boolean actual = revolutPaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

	@Test
	public void isValidNoReference_shouldReturnFalse(){
		RevolutPayment payment = new RevolutPayment("revolut", "123", 0, "EUR",
				"456", new RevolutReceiver("789", "123456"), null, "payment");
		boolean actual = revolutPaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

	@Test
	public void isValidNoType_shouldReturnFalse(){
		RevolutPayment payment = new RevolutPayment("revolut", "123", 0, "EUR",
				"456", new RevolutReceiver("789", "123456"), "ref", null);
		boolean actual = revolutPaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

}
