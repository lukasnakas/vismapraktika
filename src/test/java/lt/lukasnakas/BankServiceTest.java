package lt.lukasnakas;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.lukasnakas.model.Account;
import lt.lukasnakas.model.Transaction;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.account.DanskeAccountDetails;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.danske.transaction.DanskeTransactionAmount;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.RevolutCounterparty;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import lt.lukasnakas.model.revolut.transaction.RevolutTransactionLegs;
import lt.lukasnakas.service.BankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
public class BankServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BankService bankService;

	@Test
	public void getAccountsShouldReturnDanskeAccount() throws Exception {
		DanskeAccountDetails details = new DanskeAccountDetails("id123", "IBAN",
				"AccountName", "secondary");
		DanskeAccountDetails[] detailsArray = new DanskeAccountDetails[1];
		detailsArray[0] = details;
		Account account = new DanskeAccount("123", detailsArray);

		Map<String, Account> expected = new HashMap<>();
		expected.put(account.getId(), account);

		ObjectMapper mapper = new ObjectMapper();
		String actual = mapper.writeValueAsString(expected);

		when(bankService.getAccounts()).thenReturn(expected);
		mockMvc.perform(get("/api/accounts").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(actual));
	}

	@Test
	public void getAccountsShouldReturnRevolutAccount() throws Exception {
		Account account = new RevolutAccount("123", "AccountName", 500, "GBP",
				"active", true, "2020", "2020");

		Map<String, Account> mappedAccounts = new HashMap<>();
		mappedAccounts.put(account.getId(), account);

		ObjectMapper mapper = new ObjectMapper();
		String actual = mapper.writeValueAsString(mappedAccounts);

		when(bankService.getAccounts()).thenReturn(mappedAccounts);
		mockMvc.perform(get("/api/accounts").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(actual));
	}

	@Test
	public void getTransactionsShouldReturnDanskeTransaction() throws Exception {
		Transaction transaction = new DanskeTransaction("123", "debit", new DanskeTransactionAmount(10, "EUR"));

		Map<String, Transaction> mappedTransactions = new HashMap<>();
		mappedTransactions.put(transaction.getId(), transaction);

		ObjectMapper mapper = new ObjectMapper();
		String actual = mapper.writeValueAsString(mappedTransactions);

		when(bankService.getTransactions()).thenReturn(mappedTransactions);
		mockMvc.perform(get("/api/transactions").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(actual));
	}

	@Test
	public void getTransactionsShouldReturnRevolutTransaction() throws Exception {
		RevolutCounterparty counterparty = new RevolutCounterparty("business", "123");
		RevolutTransactionLegs legs = new RevolutTransactionLegs("123", "456", counterparty, 200, "GBP", "description",1000);
		RevolutTransactionLegs[] legsArray = new RevolutTransactionLegs[1];
		legsArray[0] = legs;
		Transaction transaction = new RevolutTransaction("123", "payment", "123456789", "2020", "ref", legsArray, "active", "2020", "2020");

		Map<String, Transaction> mappedTransactions = new HashMap<>();
		mappedTransactions.put(transaction.getId(), transaction);

		ObjectMapper mapper = new ObjectMapper();
		String actual = mapper.writeValueAsString(mappedTransactions);

		when(bankService.getTransactions()).thenReturn(mappedTransactions);
		mockMvc.perform(get("/api/transactions").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(actual));
	}

	@Test
	public void postDanskeTransactionShouldReturnTransaction() throws Exception {
		DanskeTransactionAmount amount = new DanskeTransactionAmount(550, "EUR");
		Transaction expected = new DanskeTransaction("123", "credit", amount);

		String newTransactionRequestBody = "{\n" +
				"\"template\": \"credit\",\n" +
				"\"amount\": 550\n" +
				"}";
		String actual = "{\n" +
				"    \"id\": \"123\",\n" +
				"    \"CreditDebitIndicator\": \"credit\",\n" +
				"    \"Amount\": {\n" +
				"        \"Amount\": 550.0,\n" +
				"        \"Currency\": \"EUR\"\n" +
				"    }\n" +
				"}";

		when(bankService.postTransaction(newTransactionRequestBody, "danske")).thenReturn(expected);
		mockMvc.perform(post("/api/transactions/danske")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newTransactionRequestBody))
				.andExpect(content().json(actual));
	}

	@Test
	public void postRevolutTransactionShouldReturnTransaction() throws Exception {
		Transaction expected = new RevolutTransaction("123", null, null, null, null, null, "completed", "2020-02-13", "2020-02-14");

		String newTransactionRequestBody = "{\n" +
				"\"type\":\"payment\",\n" +
				"\"account_id\":\"123456\",\n" +
				"\"receiver\":{\n" +
				"\"counterparty_id\":\"123456789\",\n" +
				"\"account_id\":\"123456789\"\n" +
				"},\n" +
				"\"amount\":655,\n" +
				"\"currency\":\"GBP\",\n" +
				"\"reference\":\"ref\"\n" +
				"}";
		String actual = "{\n" +
				"\"id\":\"123\",\n" +
				"\"state\":\"completed\",\n" +
				"\"created_at\":\"2020-02-13\",\n" +
				"\"completed_at\":\"2020-02-14\"\n" +
				"}";

		when(bankService.postTransaction(newTransactionRequestBody, "revolut")).thenReturn(expected);
		mockMvc.perform(post("/api/transactions/revolut")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newTransactionRequestBody))
				.andExpect(content().json(actual));
	}

}
