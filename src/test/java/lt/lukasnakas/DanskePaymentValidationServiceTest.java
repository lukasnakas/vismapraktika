//package lt.lukasnakas;
//
//import lt.lukasnakas.model.danske.transaction.DanskePayment;
//import lt.lukasnakas.service.danske.DanskePaymentValidationService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class DanskePaymentValidationServiceTest {
//
//	@InjectMocks
//	private DanskePaymentValidationService danskePaymentValidationService;
//
//	@Test
//	public void isValid_shouldReturnTrue(){
//		DanskePayment payment = new DanskePayment(100, "debit");
//		boolean actual = danskePaymentValidationService.isValid(payment);
//		assertTrue(actual);
//	}
//
//	@Test
//	public void isValidNoTemplate_shouldReturnFalse(){
//		DanskePayment payment = new DanskePayment(100, null);
//		boolean actual = danskePaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//	@Test
//	public void isValidNoAmount_shouldReturnFalse(){
//		DanskePayment payment = new DanskePayment(0, "credit");
//		boolean actual = danskePaymentValidationService.isValid(payment);
//		assertFalse(actual);
//	}
//
//}
