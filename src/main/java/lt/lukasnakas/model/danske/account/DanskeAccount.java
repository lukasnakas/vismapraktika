package lt.lukasnakas.model.danske.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Account;

import javax.persistence.Entity;

@Entity
public class DanskeAccount extends Account {

    @JsonProperty("Account")
    private DanskeAccountDetails[] account;

    public DanskeAccount() {
    }

    public DanskeAccount(String id, DanskeAccountDetails[] account) {
        super(id);
        this.account = account;
    }

    public DanskeAccountDetails[] getAccount() {
        return account;
    }

    public void setAccount(DanskeAccountDetails[] account) {
        this.account = account;
    }
}
