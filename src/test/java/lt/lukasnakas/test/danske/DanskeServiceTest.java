package lt.lukasnakas.test.danske;

import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.service.danske.DanskeService;
import lt.lukasnakas.service.danske.DanskeTransactionErrorService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DanskeServiceTest {

	@Mock
	private DanskeTransactionErrorService danskeTransactionErrorService;

	@InjectMocks
	private DanskeService danskeService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void getErrorWithFirstMissingParamFromPayment_shouldReturnTrue_whenComparingParamsList() {
		Payment payment = testDataGenerator.buildTransactionPayment();
		TransactionError expected = testDataGenerator.buildDanskeTransactionError();

		when(danskeTransactionErrorService.getErrorWithMissingParamsFromPayment(payment)).thenReturn(expected);

		TransactionError actual = danskeService.getErrorWithMissingParamsFromPayment(payment);

		assertEquals(expected, actual);
	}

}
