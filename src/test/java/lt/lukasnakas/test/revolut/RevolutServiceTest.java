package lt.lukasnakas.test.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.mapper.AccountMapper;
import lt.lukasnakas.mapper.AccountMapperImpl;
import lt.lukasnakas.mapper.TransactionMapper;
import lt.lukasnakas.mapper.TransactionMapperImpl;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.*;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import lt.lukasnakas.service.revolut.RevolutService;
import lt.lukasnakas.service.revolut.RevolutTransactionErrorService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RevolutServiceTest {

	@Mock
	private RevolutServiceConfiguration revolutServiceConfiguration;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private RevolutTransactionErrorService revolutTransactionErrorService;

	@Mock
	private HttpHeaders httpHeaders;

	@Mock
	private AccountMapper accountMapper;

	@Mock
	private TransactionMapper transactionMapper;

	@InjectMocks
	private RevolutService revolutService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void retrieveAccounts_shouldReturnTrue_whenComparingFirstElements() {
		ResponseEntity<List<RevolutAccount>> responseEntity = testDataGenerator.getExpectedRevolutAccountResponseEntity();
		CommonAccount commonAccount = testDataGenerator.buildCommonAccount(responseEntity.getBody().get(0));

		when(revolutService.getResponseEntityForAccounts("")).thenReturn(responseEntity);
		when(accountMapper.revolutAccountToCommonAccount(responseEntity.getBody().get(0))).thenReturn(commonAccount);

		List<CommonAccount> expected = Collections.singletonList(commonAccount);
		List<CommonAccount> actual = revolutService.retrieveAccounts();

		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void retrieveTransactions_shouldReturnTrue_whenComparingFirstElements() {
		ResponseEntity<List<RevolutTransaction>> responseEntity = testDataGenerator.getExpectedRevolutTransactionResponseEntity();
		Payment payment = testDataGenerator.buildTransactionPayment();
		CommonTransaction commonTransaction = testDataGenerator.buildCommonTransaction(responseEntity.getBody().get(0), payment);

		when(revolutService.getResponseEntityForTransactions("")).thenReturn(responseEntity);
		when(transactionMapper.revolutTransactionToCommonTransaction(responseEntity.getBody().get(0))).thenReturn(commonTransaction);

		List<CommonTransaction> expected = Collections.singletonList(commonTransaction);
		List<CommonTransaction> actual = revolutService.retrieveTransactions();

		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void postTransaction_shouldReturnTrue_whenComparingTransactionResponses() {
		ResponseEntity<RevolutTransaction> responseEntity = testDataGenerator.getExpectedRevolutTransactionResponseEntityForPost();
		Payment payment = testDataGenerator.buildTransactionPayment();
		CommonTransaction expected = testDataGenerator.buildCommonTransaction(responseEntity.getBody(), payment);

		when(revolutService.getResponseEntityForTransaction("", payment)).thenReturn(responseEntity);
		when(transactionMapper.revolutTransactionToCommonTransaction(responseEntity.getBody(), payment)).thenReturn(expected);

		CommonTransaction actual = revolutService.postTransaction(payment);

		assertEquals(expected, actual);
	}

	@Test
	public void getErrorWithFirstMissingParamFromPayment_shouldReturnTrue_whenComparingParamsList() {
		Payment payment = testDataGenerator.buildTransactionPayment();
		TransactionError expected = testDataGenerator.buildRevolutTransactionError();

		when(revolutTransactionErrorService.getErrorWithMissingParamsFromPayment(payment)).thenReturn(expected);

		TransactionError actual = revolutService.getErrorWithMissingParamsFromPayment(payment);

		assertEquals(expected, actual);
	}
}
