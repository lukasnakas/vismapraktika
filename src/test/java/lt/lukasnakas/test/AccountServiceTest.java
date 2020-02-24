package lt.lukasnakas.test;

import lt.lukasnakas.exception.AccountNotFoundException;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.repository.AccountRepository;
import lt.lukasnakas.service.AccountService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private AccountService accountService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Rule
	private ExpectedException thrown = ExpectedException.none();

	@Test
	public void getAccounts_shouldReturnTrue_whenComparingAccountLists() {
		List<CommonAccount> expected = testDataGenerator.buildCommonAccountList();

		when(accountRepository.findAll()).thenReturn(expected);
		List<CommonAccount> actual = accountService.getAccounts();

		assertEquals(expected, actual);
	}

	@Test
	public void getAccountById_shouldReturnTrue_whenSpecifiedAccountExists() {
		String id = "123";
		List<CommonAccount> commonAccountListByAccountId = testDataGenerator.buildCommonAccountList().stream()
				.filter(commonAccount -> commonAccount.getAccountId().equals(id))
				.collect(Collectors.toList());

		CommonAccount expected = null;
		if (commonAccountListByAccountId.size() > 0) {
			expected = commonAccountListByAccountId.get(0);
		}

		when(accountRepository.findById(id)).thenReturn(Optional.ofNullable(expected));

		CommonAccount actual = accountService.getAccountById(id);
		assertEquals(expected, actual);
	}

}
