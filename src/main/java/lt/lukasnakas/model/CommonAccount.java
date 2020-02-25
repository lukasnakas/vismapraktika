package lt.lukasnakas.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class CommonAccount {

	@Id
	private String accountId;
	private double balance;
	private String currency;
	private String bankName;

	public CommonAccount() {
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if(!(obj instanceof CommonAccount)){
			return false;
		}

		CommonAccount commonAccount = (CommonAccount) obj;

		return Objects.equals(accountId, commonAccount.accountId) &&
				Objects.equals(balance, commonAccount.balance) &&
				Objects.equals(bankName, commonAccount.bankName) &&
				Objects.equals(currency, commonAccount.currency);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, balance, bankName, currency);
	}
}
