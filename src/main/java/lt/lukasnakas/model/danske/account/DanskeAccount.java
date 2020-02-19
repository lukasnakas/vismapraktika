package lt.lukasnakas.model.danske.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Account;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import java.util.Arrays;

@Entity
public class DanskeAccount extends Account {

    @JsonProperty("Account")
    @OneToMany(mappedBy = "danskeAccount", cascade = CascadeType.ALL)
    @OrderColumn
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

    @Override
    public String toString() {
        return "DanskeAccount{" +
                "account=" + Arrays.toString(account) +
                '}';
    }
}
