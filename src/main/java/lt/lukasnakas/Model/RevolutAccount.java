package lt.lukasnakas.Model;

import java.util.Date;

public class RevolutAccount {

    private String name;
    private double balance;
    private boolean publicAccount;
    private Date updatedAt;
    private Date createdAt;
    private String currency;
    private String id;
    private String state;

    public RevolutAccount() {
    }

    public RevolutAccount(String name, double balance, boolean publicAccount, Date updatedAt, Date createdAt, String currency, String id, String state) {
        this.name = name;
        this.balance = balance;
        this.publicAccount = publicAccount;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.currency = currency;
        this.id = id;
        this.state = state;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
