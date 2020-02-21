package lt.lukasnakas.util;

import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.danske.account.Balance;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.account.Data;
import lt.lukasnakas.model.danske.transaction.DanskeTransactionAmount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MockedDataGenerator {

	public ResponseEntity<DanskeAccount> getMockedDanskeAccountResponseEntity() {
		Balance[] balances = new Balance[1];
		balances[0] = new Balance("123", "debit", "type", "2020",
				new DanskeTransactionAmount(55, "EUR"));
		DanskeAccount danskeAccount = new DanskeAccount("456", new Data(balances));
		return new ResponseEntity<>(danskeAccount, HttpStatus.ACCEPTED);
	}

	public CommonAccount generateCommonAccount(DanskeAccount danskeAccount) {
		return new CommonAccount("Danske",
				danskeAccount.getData().getBalance()[0].getAccountId(),
				danskeAccount.getData().getBalance()[0].getAmount().getAmount(),
				danskeAccount.getData().getBalance()[0].getAmount().getCurrency());
	}

}
