package lt.lukasnakas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payment {

    @JsonProperty("sender_account_id")
    private String senderAccountId;
    @JsonProperty("receiver_account_id")
    private String receiverAccountId;
    @JsonProperty("counterparty_id")
    private String counterpartyId;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("description")
    private String description;

    public Payment() {
    }

    public Payment(String senderAccountId, String receiverAccountId, String counterpartyId, double amount,
                   String currency, String description) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.counterpartyId = counterpartyId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
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

    @Override
    public String toString() {
        return "Payment{" +
                "senderAccountId='" + senderAccountId + '\'' +
                ", receiverAccountId='" + receiverAccountId + '\'' +
                ", counterpartyId='" + counterpartyId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
