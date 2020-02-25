package lt.lukasnakas.test.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.mapper.AccountMapper;
import lt.lukasnakas.mapper.AccountMapperImpl;
import lt.lukasnakas.mapper.TransactionMapper;
import lt.lukasnakas.mapper.TransactionMapperImpl;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.service.danske.DanskeService;
import lt.lukasnakas.service.danske.DanskeTransactionErrorService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DanskeServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private DanskeServiceConfiguration danskeServiceConfiguration;

	@Mock
	private HttpHeaders httpHeaders;

	@Mock
	private DanskeTransactionErrorService danskeTransactionErrorService;

	@Mock
	private TransactionMapper transactionMapper;

	@Mock
	private AccountMapper accountMapper;

	@InjectMocks
	private DanskeService danskeService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void retrieveAccounts_shouldReturnTrue_whenComparingFirstElements() {
		ResponseEntity<DanskeAccount> responseEntity = testDataGenerator.getExpectedDanskeAccountResponseEntity();
		CommonAccount commonAccount = testDataGenerator.buildCommonAccount(responseEntity.getBody());

		when(danskeService.getResponseEntityForAccounts()).thenReturn(responseEntity);
		when(accountMapper.danskeAccountToCommonAccount(responseEntity.getBody())).thenReturn(commonAccount);

		List<CommonAccount> expected = Collections.singletonList(commonAccount);
		List<CommonAccount> actual = danskeService.retrieveAccounts();

		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void retrieveTransactions_shouldReturnTrue_whenComparingFirstElements() {
		ResponseEntity<List<DanskeTransaction>> responseEntity = testDataGenerator.getExpectedDanskeTransactionResponseEntity();
		CommonTransaction commonTransaction = testDataGenerator.buildCommonTransaction(responseEntity.getBody().get(0));

		when(danskeService.getResponseEntityForTransactions("")).thenReturn(responseEntity);
		when(transactionMapper.danskeTransactionToCommonTransaction(responseEntity.getBody().get(0))).thenReturn(commonTransaction);

		List<CommonTransaction> expected = Collections.singletonList(commonTransaction);
		List<CommonTransaction> actual = danskeService.retrieveTransactions();

		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void postTransaction_shouldReturnTrue_whenComparingTransactionResponses() {
		ResponseEntity<DanskeTransaction> responseEntity = testDataGenerator.getExpectedDanskeTransactionResponseEntityForPost();
		CommonTransaction expected = testDataGenerator.buildCommonTransaction(responseEntity.getBody());
		Payment payment = testDataGenerator.buildTransactionPayment();

		when(danskeService.getResponseEntityForTransaction("", payment)).thenReturn(responseEntity);
		when(transactionMapper.danskeTransactionToCommonTransaction(responseEntity.getBody())).thenReturn(expected);

		CommonTransaction actual = danskeService.postTransaction(payment);

		assertEquals(expected, actual);
	}

	@Test
	public void getErrorWithFirstMissingParamFromPayment_shouldReturnTrue_whenComparingParamsList() {
		Payment payment = testDataGenerator.buildTransactionPayment();
		TransactionError expected = testDataGenerator.buildDanskeTransactionError();

		when(danskeTransactionErrorService.getErrorWithMissingParamsFromPayment(payment)).thenReturn(expected);

		TransactionError actual = danskeService.getErrorWithMissingParamsFromPayment(payment);

		assertEquals(expected, actual);
	}

}
