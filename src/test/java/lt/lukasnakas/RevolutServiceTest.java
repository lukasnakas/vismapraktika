package lt.lukasnakas;

import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import lt.lukasnakas.service.revolut.RevolutService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RevolutServiceTest {

	@InjectMocks
	private RevolutService revolutService;

	@Test
	public void getParsedAccountsList_returnsAccountsList(){
		RevolutAccount account = new RevolutAccount("123", "AccName", 500, "EUR", "active", true, "", "");
		List<RevolutAccount> revolutAccountsList = Lists.newArrayList(account);

		List<Account> expected = new ArrayList<>(revolutAccountsList);
		List<Account> actual = revolutService.getParsedAccountsList(revolutAccountsList);
		assertEquals(expected, actual);
	}

	@Test
	public void getParsedTransactionsList_returnsTransactionsList(){
		RevolutTransaction transaction = new RevolutTransaction("123", "payment", "111", "", "ref", null, "completed", "", "");
		List<RevolutTransaction> revolutTransactionsList = Lists.newArrayList(transaction);

		List<Transaction> expected = new ArrayList<>(revolutTransactionsList);
		List<Transaction> actual = revolutService.getParsedTransactionsList(revolutTransactionsList);
		assertEquals(expected, actual);
	}

}
