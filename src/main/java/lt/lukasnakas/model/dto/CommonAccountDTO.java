package lt.lukasnakas.model.dto;

import java.util.Objects;

public class CommonAccountDTO {

	private String accountId;
	private double balance;
	private String currency;
	private String bankName;

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
	public int hashCode() {
		return Objects.hash(accountId, balance, currency, bankName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if(!(obj instanceof CommonAccountDTO)){
			return false;
		}

		CommonAccountDTO commonAccountDTO = (CommonAccountDTO) obj;

		return Objects.equals(accountId, commonAccountDTO.accountId) &&
				Objects.equals(balance, commonAccountDTO.balance) &&
				Objects.equals(bankName, commonAccountDTO.bankName) &&
				Objects.equals(currency, commonAccountDTO.currency);
	}
}
