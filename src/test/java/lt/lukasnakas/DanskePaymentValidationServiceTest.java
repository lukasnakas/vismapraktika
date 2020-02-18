package lt.lukasnakas;

import lt.lukasnakas.model.Payment;
import lt.lukasnakas.service.danske.DanskePaymentValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DanskePaymentValidationServiceTest {

	@InjectMocks
	private DanskePaymentValidationService danskePaymentValidationService;

	@Test
	public void isValid_shouldReturnTrue(){
		Payment payment = new Payment(null, null, null, 110, null, null);
		boolean actual = danskePaymentValidationService.isValid(payment);
		assertTrue(actual);
	}

	@Test
	public void isValidNoAmount_shouldReturnFalse(){
		Payment payment = new Payment(null, null, null, 0, null, null);
		boolean actual = danskePaymentValidationService.isValid(payment);
		assertFalse(actual);
	}

}
