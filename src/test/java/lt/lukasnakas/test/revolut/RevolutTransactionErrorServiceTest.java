package lt.lukasnakas.test.revolut;

import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.service.revolut.RevolutTransactionErrorService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RevolutTransactionErrorServiceTest {

	@InjectMocks
	private RevolutTransactionErrorService revolutTransactionErrorService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void getErrorWithMissingParamsFromPayment_shouldReturnTrue_when() {
		RevolutPayment revolutPayment = testDataGenerator.buildInvalidRevolutTransactionPayment();

		TransactionError expected = testDataGenerator.buildRevolutTransactionError();
		TransactionError actual = revolutTransactionErrorService.getErrorWithMissingParamsFromPayment(revolutPayment);

		assertEquals(expected, actual);
	}



}
