package lt.lukasnakas.model.revolut.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Account;
import java.util.Date;

public class RevolutAccount extends Account {
    private String name;
    private double balance;
    private String currency;
    private String state;
    private boolean publicAccount;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;

    public RevolutAccount() {
    }

    public RevolutAccount(String id, String name, double balance, String currency, String state,
                          boolean publicAccount, Date createdAt, Date updatedAt) {
        super(id);
        this.name = name;
        this.balance = balance;
        this.currency = currency;
        this.state = state;
        this.publicAccount = publicAccount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isPublicAccount() {
        return publicAccount;
    }

    public void setPublicAccount(boolean publicAccount) {
        this.publicAccount = publicAccount;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}