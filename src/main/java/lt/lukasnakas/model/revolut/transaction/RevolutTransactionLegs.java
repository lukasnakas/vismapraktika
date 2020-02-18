package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutTransactionLegs {

    @JsonProperty("leg_id")
    private String id;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("counterparty")
    private RevolutCounterparty counterparty;
    @JsonProperty("bill_amount")
    private double amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("description")
    private String description;
    @JsonProperty("balance")
    private double balance;

    public RevolutTransactionLegs() {
    }

    public RevolutTransactionLegs(String id, String accountId, RevolutCounterparty counterparty,
                                  double amount, String currency, String description, double balance) {
        this.id = id;
        this.accountId = accountId;
        this.counterparty = counterparty;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public RevolutCounterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(RevolutCounterparty counterparty) {
        this.counterparty = counterparty;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
