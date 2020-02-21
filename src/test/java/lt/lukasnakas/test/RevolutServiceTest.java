package lt.lukasnakas.test;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.error.TransactionError;
import lt.lukasnakas.model.*;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import lt.lukasnakas.service.CommonEntityMapperService;
import lt.lukasnakas.service.revolut.RevolutService;
import lt.lukasnakas.service.revolut.RevolutTransactionErrorService;
import lt.lukasnakas.util.MockedDataGenerator;
import org.assertj.core.util.Lists;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RevolutServiceTest {

	@Mock
	private RevolutServiceConfiguration revolutServiceConfiguration;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private CommonEntityMapperService commonEntityMapperService;

	@Mock
	private RevolutTransactionErrorService revolutTransactionErrorService;

	@Mock
	private HttpHeaders httpHeaders;

	@InjectMocks
	private RevolutService revolutService;

	private MockedDataGenerator mockedDataGenerator = new MockedDataGenerator();

	@Test
	public void retrieveAccounts_shouldReturnTrue_whenComparingFirstElements() {
		ResponseEntity<List<RevolutAccount>> responseEntity = mockedDataGenerator.getMockedRevolutAccountResponseEntity();
		CommonAccount commonAccount = mockedDataGenerator.buildCommonAccount(responseEntity.getBody().get(0));

		when(revolutService.getResponseEntityForAccounts("")).thenReturn(responseEntity);
		when(commonEntityMapperService.convertToCommonAccount(responseEntity.getBody().get(0))).thenReturn(commonAccount);

		List<CommonAccount> expected = Collections.singletonList(commonAccount);
		List<CommonAccount> actual = revolutService.retrieveAccounts();

		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void retrieveTransactions_shouldReturnTrue_whenComparingFirstElements() {
		ResponseEntity<List<RevolutTransaction>> responseEntity = mockedDataGenerator.getMockedRevolutTransactionResponseEntity();
		Payment payment = mockedDataGenerator.buildRevolutTransactionPayment();
		CommonTransaction commonTransaction = mockedDataGenerator.buildCommonTransaction(responseEntity.getBody().get(0), payment);

		when(revolutService.getResponseEntityForTransactions("")).thenReturn(responseEntity);
		when(commonEntityMapperService.convertToCommonTransaction(responseEntity.getBody().get(0))).thenReturn(commonTransaction);

		List<CommonTransaction> expected = Collections.singletonList(commonTransaction);
		List<CommonTransaction> actual = revolutService.retrieveTransactions();

		assertEquals(expected.get(0), actual.get(0));
	}

	@Test
	public void postTransaction_shouldReturnTrue_whenComparingTransactionResponses() {
		ResponseEntity<RevolutTransaction> responseEntity = mockedDataGenerator.getMockedRevolutTransactionResponseEntityForPost();
		Payment payment = mockedDataGenerator.buildRevolutTransactionPayment();
		CommonTransaction expected = mockedDataGenerator.buildCommonTransaction(responseEntity.getBody(), payment);

		when(revolutService.getResponseEntityForTransaction("", payment)).thenReturn(responseEntity);
		when(commonEntityMapperService.convertToCommonTransaction(responseEntity.getBody())).thenReturn(expected);

		CommonTransaction actual = revolutService.postTransaction(payment);

		assertEquals(expected, actual);
	}

	@Test
	public void getResponseEntityForAccounts_shouldReturnTrue_whenComparingResponseEntities() {
		ResponseEntity<List<RevolutAccount>> expected = mockedDataGenerator.getMockedRevolutAccountResponseEntity();
		String url = "https://sandbox-b2b.revolut.com/api/1.0/accounts";

		when(revolutServiceConfiguration.getUrlAccounts()).thenReturn(url);
		when(restTemplate.exchange(
				Mockito.eq(url),
				Mockito.eq(HttpMethod.GET),
				Mockito.any(),
				Mockito.<ParameterizedTypeReference<List<RevolutAccount>>>any()
		)).thenReturn(expected);

		ResponseEntity<List<RevolutAccount>> actual = revolutService.getResponseEntityForAccounts("");

		assertEquals(expected, actual);
	}

	@Test
	public void getResponseEntityForTransactions_shouldReturnTrue_whenComparingResponseEntities() {
		ResponseEntity<List<RevolutTransaction>> expected = mockedDataGenerator.getMockedRevolutTransactionResponseEntity();
		String url = "https://sandbox-b2b.revolut.com/api/1.0/transactions";

		when(revolutServiceConfiguration.getUrlAccountTransactions()).thenReturn(url);
		when(restTemplate.exchange(
				Mockito.eq(url),
				Mockito.eq(HttpMethod.GET),
				Mockito.any(),
				Mockito.<ParameterizedTypeReference<List<RevolutTransaction>>>any()
		)).thenReturn(expected);

		ResponseEntity<List<RevolutTransaction>> actual = revolutService.getResponseEntityForTransactions("");

		assertEquals(expected, actual);
	}

	@Test
	public void getResponseEntityForTransaction_shouldReturnTrue_whenComparingResponseEntities() {
		ResponseEntity<RevolutTransaction> expected = mockedDataGenerator.getMockedRevolutTransactionResponseEntityForPost();
		Payment payment = mockedDataGenerator.buildDanskeTransactionPayment();
		String url = "https://sandbox-b2b.revolut.com/api/1.0/pay";

		when(revolutServiceConfiguration.getUrlAccountTransactions()).thenReturn(url);
		when(restTemplate.exchange(
				Mockito.eq(url),
				Mockito.eq(HttpMethod.POST),
				Mockito.any(),
				Mockito.<ParameterizedTypeReference<RevolutTransaction>>any()
		)).thenReturn(expected);

		ResponseEntity<RevolutTransaction> actual = revolutService.getResponseEntityForTransaction("", payment);

		assertEquals(expected, actual);
	}

	@Test
	public void getErrorWithFirstMissingParamFromPayment_shouldReturnTrue_whenComparingParamsList() {
		Payment payment = mockedDataGenerator.buildRevolutTransactionPayment();
		TransactionError expected = mockedDataGenerator.buildRevolutTransactionError();

		when(revolutTransactionErrorService.getErrorWithMissingParamsFromPayment(payment)).thenReturn(expected);

		TransactionError actual = revolutService.getErrorWithMissingParamsFromPayment(payment);

		assertEquals(expected, actual);
	}

}
