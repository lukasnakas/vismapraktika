package lt.lukasnakas;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.danske.account.Balance;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.account.Data;
import lt.lukasnakas.model.danske.transaction.DanskeTransactionAmount;
import lt.lukasnakas.service.danske.DanskeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DanskeServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private DanskeServiceConfiguration danskeServiceConfiguration;

	@Mock
	private HttpHeaders httpHeaders;

	@InjectMocks
	private DanskeService danskeService;

	@Test
	public void retrieveAccounts_shouldReturn() {
		Balance[] balances = new Balance[1];
		balances[0] = new Balance("123", "debit", "type", "2020",
				new DanskeTransactionAmount(55, "EUR"));
		DanskeAccount danskeAccount = new DanskeAccount("456", new Data(balances));
		ResponseEntity<DanskeAccount> responseEntity = new ResponseEntity<>(danskeAccount, HttpStatus.ACCEPTED);

		when(danskeService.getResponseEntityForAccounts()).thenReturn(responseEntity);

		CommonAccount commonAccount = new CommonAccount("Danske",
				responseEntity.getBody().getData().getBalance()[0].getAccountId(),
				responseEntity.getBody().getData().getBalance()[0].getAmount().getAmount(),
				responseEntity.getBody().getData().getBalance()[0].getAmount().getCurrency());

		List<CommonAccount> expected = Collections.singletonList(commonAccount);
		List<CommonAccount> actual = danskeService.retrieveAccounts();

		assertEquals(expected.get(0), actual.get(0));
	}

}
