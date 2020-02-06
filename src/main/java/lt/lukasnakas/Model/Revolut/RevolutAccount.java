package lt.lukasnakas.Model.Revolut;

import java.util.Date;

public class RevolutAccount {

    private String id;
    private String name;
    private double balance;
    private String currency;
    private String state;
    private boolean publicAccount;
    private Date created_at;
    private Date updated_at;

    public RevolutAccount() {
    }

    public RevolutAccount(String name, double balance, boolean publicAccount, Date updated_at, Date created_at, String currency, String id, String state) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.currency = currency;
        this.state = state;
        this.publicAccount = publicAccount;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
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
