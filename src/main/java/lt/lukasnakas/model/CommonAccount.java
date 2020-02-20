package lt.lukasnakas.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CommonAccount {

	@Id
	private String accountId;
	private double balance;
	private String currency;
	private String bankName;

	public CommonAccount() {
	}

	public CommonAccount(String bankName, String accountId, double balance, String currency) {
		this.bankName = bankName;
		this.accountId = accountId;
		this.balance = balance;
		this.currency = currency;
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
	public String toString() {
		return "CommonAccount{" +
				"bankName='" + bankName + '\'' +
				", accountId='" + accountId + '\'' +
				", amount=" + balance +
				", currency='" + currency + '\'' +
				'}';
	}
}