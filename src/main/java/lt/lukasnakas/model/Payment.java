package lt.lukasnakas.model;

public class Payment {

	private String bankName;

	public Payment() {
	}

	public Payment(String bankName) {
		this.bankName = bankName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
