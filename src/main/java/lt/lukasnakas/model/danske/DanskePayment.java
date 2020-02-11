package lt.lukasnakas.model.danske;

import lt.lukasnakas.model.Payment;

public class DanskePayment extends Payment {

	private String template;
	private double amount;

	public DanskePayment() {
	}

	public DanskePayment(String bankName, String template, double amount) {
		super(bankName);
		this.template = template;
		this.amount = amount;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
