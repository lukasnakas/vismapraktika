package lt.lukasnakas;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.account.DanskeAccountDetails;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.danske.transaction.DanskeTransactionAmount;
import lt.lukasnakas.service.danske.DanskeService;
import lt.lukasnakas.service.danske.DanskeTokenRenewalService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DanskeServiceTest {

	@InjectMocks
	private DanskeService danskeService;

	@Test
	public void getParsedAccountsList_returnsAccountsList(){
		DanskeAccount account = new DanskeAccount("123", new DanskeAccountDetails[] {
				new DanskeAccountDetails("id123", "IBAN","AccountName", "secondary")
		});
		List<DanskeAccount> danskeAccountsList = Lists.newArrayList(account);

		List<Account> expected = new ArrayList<>(danskeAccountsList);
		List<Account> actual = danskeService.getParsedAccountsList(danskeAccountsList);
		assertEquals(expected, actual);
	}

	@Test
	public void getParsedTransactionsList_returnsTransactionsList(){
		DanskeTransaction transaction = new DanskeTransaction("123", "debit", new DanskeTransactionAmount(100, "EUR"));
		List<DanskeTransaction> danskeTransactionsList = Lists.newArrayList(transaction);

		List<Transaction> expected = new ArrayList<>(danskeTransactionsList);
		List<Transaction> actual = danskeService.getParsedTransactionsList(danskeTransactionsList);
		assertEquals(expected, actual);
	}

}
