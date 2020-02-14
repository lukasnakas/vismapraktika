//package lt.lukasnakas;
//
//import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
//import lt.lukasnakas.model.revolut.transaction.RevolutReceiver;
//import lt.lukasnakas.service.revolut.RevolutPaymentValidationService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class RevolutPaymentValidationServiceTest {
//
//	@InjectMocks
//	private RevolutPaymentValidationService revolutPaymentValidationService;
//
//	@Test
//	public void isValid_shouldReturnTrue(){
//		RevolutPayment payment = new RevolutPayment(100, "123",
//				new RevolutReceiver("789", "123456"),
//				"ref", "456", "EUR");
//		boolean actual = revolutPaymentValidationService.isValid(payment);
//		assertTrue(actual);
//	}
//
//	@Test
//	public void isValidNoAmount_shouldReturnFalse(){
//		RevolutPayment payment = new RevolutPayment(0, "123",
//				new RevolutReceiver("789", "123456"),
//				"ref", "456", "EUR");
//		boolean actual = revolutPaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//	@Test
//	public void isValidNegativeAmount_shouldReturnFalse(){
//		RevolutPayment payment = new RevolutPayment(-10, "123",
//				new RevolutReceiver("789", "123456"),
//				"456", "ref", "EUR");
//		boolean actual = revolutPaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//	@Test
//	public void isValidNoCurrency_shouldReturnFalse(){
//		RevolutPayment payment = new RevolutPayment(0, "123",
//				new RevolutReceiver("789", "123456"),
//				"456", "ref", "EUR");
//		boolean actual = revolutPaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//	@Test
//	public void isValidNoAccountId_shouldReturnFalse(){
//		RevolutPayment payment = new RevolutPayment(0, "123",
//				new RevolutReceiver("789", "123456"),
//				null, "ref", "EUR");
//		boolean actual = revolutPaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//	@Test
//	public void isValidNoRevolutReceiver_shouldReturnFalse(){
//		RevolutPayment payment = new RevolutPayment(0, "123",
//				null, "456", "123", "EUR");
//		boolean actual = revolutPaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//	@Test
//	public void isValidNoRevolutReceiverCounterpartyId_shouldReturnFalse(){
//		RevolutPayment payment = new RevolutPayment(0, "123",
//				new RevolutReceiver(null, "123456"),
//				"ref", "456", "EUR");
//		boolean actual = revolutPaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//	@Test
//	public void isValidNoRevolutReceiverAccountId_shouldReturnFalse(){
//		RevolutPayment payment = new RevolutPayment(0, "123",
//				new RevolutReceiver("789", null),
//				"ref", "456", "EUR");
//		boolean actual = revolutPaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//	@Test
//	public void isValidNoReference_shouldReturnFalse(){
//		RevolutPayment payment = new RevolutPayment(0, "123",
//				new RevolutReceiver("789", "123456"),
//				"ref", null, "EUR");
//		boolean actual = revolutPaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//	@Test
//	public void isValidNoType_shouldReturnFalse(){
//		RevolutPayment payment = new RevolutPayment(0, "123",
//				new RevolutReceiver("789", "123456"),
//				"ref", "456", null);
//		boolean actual = revolutPaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//}
