package lt.lukasnakas;

import lt.lukasnakas.controller.AccountsController;
import lt.lukasnakas.controller.TransactionsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ControllerTest {

	@Autowired
	private AccountsController accountsController;

	@Autowired
	private TransactionsController transactionsController;

	@Test
	public void accountsControllerLoads() throws Exception{
		assertThat(accountsController).isNotNull();
	}

	@Test
	public void transactionsControllerLoads() throws Exception{
		assertThat(transactionsController).isNotNull();
	}

}
