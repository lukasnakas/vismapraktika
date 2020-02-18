package lt.lukasnakas;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.account.DanskeAccountDetails;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.danske.transaction.DanskeTransactionAmount;
import lt.lukasnakas.service.danske.DanskeService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DanskeServiceTest {

	@InjectMocks
	private DanskeService danskeService;

	@Test
	public void getParsedAccountsList_shouldReturnAccountsList() {
		DanskeAccount account = new DanskeAccount("123", new DanskeAccountDetails[]{
				new DanskeAccountDetails("id123", "IBAN", "AccountName", "secondary")
		});
		List<DanskeAccount> danskeAccountsList = Lists.newArrayList(account);

		List<Account> expected = new ArrayList<>(danskeAccountsList);
		List<Account> actual = danskeService.getParsedAccountsList(danskeAccountsList);
		assertEquals(expected, actual);
	}

	@Test
	public void getParsedTransactionsList_shouldReturnTransactionsList() {
		DanskeTransaction transaction = new DanskeTransaction("123", "456",
				"debit", new DanskeTransactionAmount(100, "EUR"));
		List<DanskeTransaction> danskeTransactionsList = Lists.newArrayList(transaction);

		List<Transaction> expected = new ArrayList<>(danskeTransactionsList);
		List<Transaction> actual = danskeService.getParsedTransactionsList(danskeTransactionsList);
		assertEquals(expected, actual);
	}

}
