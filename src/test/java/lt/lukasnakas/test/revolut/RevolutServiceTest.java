package lt.lukasnakas.test.revolut;

import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.*;
import lt.lukasnakas.service.revolut.RevolutService;
import lt.lukasnakas.service.revolut.RevolutTransactionErrorService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RevolutServiceTest {

	@Mock
	private RevolutTransactionErrorService revolutTransactionErrorService;

	@InjectMocks
	private RevolutService revolutService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void getErrorWithFirstMissingParamFromPayment_shouldReturnTrue_whenComparingParamsList() {
		Payment payment = testDataGenerator.buildTransactionPayment();
		TransactionError expected = testDataGenerator.buildRevolutTransactionError();

		when(revolutTransactionErrorService.getErrorWithMissingParamsFromPayment(payment)).thenReturn(expected);

		TransactionError actual = revolutService.getErrorWithMissingParamsFromPayment(payment);

		assertEquals(expected, actual);
	}
}
