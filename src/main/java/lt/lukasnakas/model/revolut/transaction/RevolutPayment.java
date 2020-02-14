package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonAlias;
import lt.lukasnakas.model.Payment;
import org.apache.commons.lang3.RandomStringUtils;

public class RevolutPayment extends Payment {

	@JsonAlias("account_id")
	private String accountId;
	@JsonAlias("receiver")
	private RevolutReceiver receiver;
	@JsonAlias("currency")
	private String currency;
	@JsonAlias("reference")
	private String reference;
	@JsonAlias("request_id")
	private String requestId;

	public RevolutPayment() {
	}

	public RevolutPayment(String accountId, RevolutReceiver receiver, String currency, String reference) {
		this.accountId = accountId;
		this.receiver = receiver;
		this.currency = currency;
		this.reference = reference;
		this.requestId = generateRequestId();
	}

	public String generateRequestId(){
		return RandomStringUtils.randomAlphanumeric(40);
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public RevolutReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(RevolutReceiver receiver) {
		this.receiver = receiver;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "RevolutPayment{" +
				"accountId='" + accountId + '\'' +
				", receiver=" + receiver +
				", currency='" + currency + '\'' +
				", reference='" + reference + '\'' +
				", requestId='" + requestId + '\'' +
				", amount='" + getAmount() + '\'' +
				'}';
	}
}
