package lt.lukasnakas.test.danske;

import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.service.danske.DanskeTransactionErrorService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DanskeTransactionErrorServiceTest {

	@InjectMocks
	private DanskeTransactionErrorService danskeTransactionErrorService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void getErrorWithMissingParamsFromPayment_shouldReturnTrue_whenErrorsMatch() {
		Payment payment = testDataGenerator.buildDanskeTransactionPayment();

		TransactionError expected = testDataGenerator.buildDanskeTransactionError();
		TransactionError actual = danskeTransactionErrorService.getErrorWithMissingParamsFromPayment(payment);

		assertEquals(expected, actual);
	}

}
