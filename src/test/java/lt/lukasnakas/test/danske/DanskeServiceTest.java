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

	@InjectMocks
	private DanskeService danskeService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void retrieveAccounts_shouldReturnTrue_whenComparingFirstElements() {
		ResponseEntity<DanskeAccount> responseEntity = testDataGenerator.getExpectedDanskeAccountResponseEntity();
		CommonAccount commonAccount = testDataGenerator.buildCommonAccount(responseEntity.getBody());

		when(danskeService.getResponseEntityForAccounts()).thenReturn(responseEntity);

		List<CommonAccount> expected = Collections.singletonList(commonAccount);
		List<CommonAccount> actual = danskeService.retrieveAccounts();

		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void retrieveTransactions_shouldReturnTrue_whenComparingFirstElements() {
		ResponseEntity<List<DanskeTransaction>> responseEntity = testDataGenerator.getExpectedDanskeTransactionResponseEntity();
		CommonTransaction commonTransaction = testDataGenerator.buildCommonTransaction(responseEntity.getBody().get(0));

		when(danskeService.getResponseEntityForTransactions("")).thenReturn(responseEntity);

		List<CommonTransaction> expected = Collections.singletonList(commonTransaction);
		List<CommonTransaction> actual = danskeService.retrieveTransactions();

		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void postTransaction_shouldReturnTrue_whenComparingTransactionResponses() {
		ResponseEntity<DanskeTransaction> responseEntity = testDataGenerator.getExpectedDanskeTransactionResponseEntityForPost();
		CommonTransaction expected = testDataGenerator.buildCommonTransaction(responseEntity.getBody());
		Payment payment = testDataGenerator.buildDanskeTransactionPayment();

		when(danskeService.getResponseEntityForTransaction("", payment)).thenReturn(responseEntity);

		CommonTransaction actual = danskeService.postTransaction(payment);

		assertEquals(expected, actual);
	}

	@Test
	public void getResponseEntityForAccounts_shouldReturnTrue_whenComparingResponseEntities() {
		ResponseEntity<DanskeAccount> expected = testDataGenerator.getExpectedDanskeAccountResponseEntity();
		String url = "https://developers.danskebank.com/_/virtualbank-api/aisp/v3.1/accounts/34289220-ff43-4019-8e79-d3884312f2c3/balances";

		when(danskeServiceConfiguration.getUrlAccountsVirtual()).thenReturn(url);
		when(restTemplate.exchange(
				Mockito.eq(url),
				Mockito.eq(HttpMethod.GET),
				Mockito.any(),
				Mockito.<ParameterizedTypeReference<DanskeAccount>>any()
		)).thenReturn(expected);

		ResponseEntity<DanskeAccount> actual = danskeService.getResponseEntityForAccounts();

		assertEquals(expected, actual);
	}

	@Test
	public void getResponseEntityForTransactions_shouldReturnTrue_whenComparingResponseEntities() {
		ResponseEntity<List<DanskeTransaction>> expected = testDataGenerator.getExpectedDanskeTransactionResponseEntity();
		String url = "https://developers.danskebank.com/_/playground-api/v1.0/41aa8b6a-60cc-475f-8348-331d5fc5d4f5" +
				"/customer/27fc10ec-6d37-491e-ae7f-49927a7ef4e7/account/34289220-ff43-4019-8e79-d3884312f2c3/transaction";

		when(danskeServiceConfiguration.getUrlAccountTransactions()).thenReturn(url);
		when(restTemplate.exchange(
				Mockito.eq(url),
				Mockito.eq(HttpMethod.GET),
				Mockito.any(),
				Mockito.<ParameterizedTypeReference<List<DanskeTransaction>>>any()
		)).thenReturn(expected);

		ResponseEntity<List<DanskeTransaction>> actual = danskeService.getResponseEntityForTransactions("");

		assertEquals(expected, actual);
	}

	@Test
	public void getResponseEntityForTransaction_shouldReturnTrue_whenComparingResponseEntities() {
		ResponseEntity<DanskeTransaction> expected = testDataGenerator.getExpectedDanskeTransactionResponseEntityForPost();
		Payment payment = testDataGenerator.buildDanskeTransactionPayment();
		String url = "https://developers.danskebank.com/_/playground-api/v1.0/41aa8b6a-60cc-475f-8348-331d5fc5d4f5" +
				"/customer/27fc10ec-6d37-491e-ae7f-49927a7ef4e7/account/34289220-ff43-4019-8e79-d3884312f2c3/transaction";

		when(danskeServiceConfiguration.getUrlAccountTransactions()).thenReturn(url);
		when(restTemplate.exchange(
				Mockito.eq(url),
				Mockito.eq(HttpMethod.POST),
				Mockito.any(),
				Mockito.<ParameterizedTypeReference<DanskeTransaction>>any()
		)).thenReturn(expected);

		ResponseEntity<DanskeTransaction> actual = danskeService.getResponseEntityForTransaction("", payment);

		assertEquals(expected, actual);
	}

	@Test
	public void getErrorWithFirstMissingParamFromPayment_shouldReturnTrue_whenComparingParamsList() {
		Payment payment = testDataGenerator.buildDanskeTransactionPayment();
		TransactionError expected = testDataGenerator.buildDanskeTransactionError();

		when(danskeTransactionErrorService.getErrorWithMissingParamsFromPayment(payment)).thenReturn(expected);

		TransactionError actual = danskeService.getErrorWithMissingParamsFromPayment(payment);

		assertEquals(expected, actual);
	}

}
