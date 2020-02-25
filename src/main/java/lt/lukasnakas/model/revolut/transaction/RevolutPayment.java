package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RevolutPayment extends Payment {

    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("receiver")
    private RevolutReceiver receiver;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("amount")
    private double amount;

    public RevolutPayment() {
    }

    public void setGeneratedRequestId() {
        this.requestId = RandomStringUtils.randomAlphanumeric(40);
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

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
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
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, receiver, currency, reference, amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if(!(obj instanceof RevolutPayment)){
            return false;
        }

        RevolutPayment revolutPayment = (RevolutPayment) obj;

        return Objects.equals(accountId, revolutPayment.accountId) &&
                Objects.equals(receiver, revolutPayment.receiver) &&
                Objects.equals(currency, revolutPayment.currency) &&
                Objects.equals(amount, revolutPayment.amount) &&
                Objects.equals(reference, revolutPayment.reference);
    }
}
