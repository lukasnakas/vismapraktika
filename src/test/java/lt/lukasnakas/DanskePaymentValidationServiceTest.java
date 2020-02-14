package lt.lukasnakas;

import lt.lukasnakas.model.danske.transaction.DanskePayment;
import lt.lukasnakas.service.danske.DanskePaymentValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DanskePaymentValidationServiceTest {

	@InjectMocks
	private DanskePaymentValidationService danskePaymentValidationService;

	@Test
	public void isValid_shouldReturnTrue(){
		DanskePayment payment = new DanskePayment("danske", "debit", 100);
		boolean actual = danskePaymentValidationService.isValid(payment);
		assertEquals(true, actual);
	}

	@Test
	public void isValidNoTemplate_shouldReturnFalse(){
		DanskePayment payment = new DanskePayment("danske", null, 100);
		boolean actual = danskePaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

	@Test
	public void isValidNoAmount_shouldReturnFalse(){
		DanskePayment payment = new DanskePayment("danske", "credit", 0);
		boolean actual = danskePaymentValidationService.isValid(payment);
		assertEquals(false, actual);
	}

}
