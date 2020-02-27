package lt.lukasnakas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String senderAccountId;
    private String receiverAccountId;
    private String counterpartyId;
    private double amount;
    private String currency;
    private String description;
    private String bankName;
    private String paymentStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(String senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(String receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public String getCounterpartyId() {
        return counterpartyId;
    }

    public void setCounterpartyId(String counterpartyId) {
        this.counterpartyId = counterpartyId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderAccountId, receiverAccountId, counterpartyId, amount, description, currency, bankName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if(!(obj instanceof Payment)){
            return false;
        }

        Payment payment = (Payment) obj;

        return Objects.equals(senderAccountId, payment.senderAccountId) &&
                Objects.equals(receiverAccountId, payment.receiverAccountId) &&
                Objects.equals(counterpartyId, payment.counterpartyId) &&
                Objects.equals(amount, payment.amount) &&
                Objects.equals(description, payment.description) &&
                Objects.equals(currency, payment.currency) &&
                Objects.equals(bankName, payment.bankName);
    }
}