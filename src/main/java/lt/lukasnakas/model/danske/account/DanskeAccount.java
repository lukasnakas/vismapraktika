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
    @JsonProperty("Currency")
    private String currency;
    @JsonProperty("AccountType")
    private String accountType;
    @JsonProperty("AccountSubType")
    private String accountSubType;
    @JsonProperty("Description")
    private String description;

    public DanskeAccount() {
    }

    public DanskeAccount(String id, DanskeAccountDetails[] account, String currency, String accountType,
                         String accountSubType, String description) {
        super(id);
        this.account = account;
        this.currency = currency;
        this.accountType = accountType;
        this.accountSubType = accountSubType;
        this.description = description;
    }

    public DanskeAccountDetails[] getAccount() {
        return account;
    }

    public void setAccount(DanskeAccountDetails[] account) {
        this.account = account;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountSubType() {
        return accountSubType;
    }

    public void setAccountSubType(String accountSubType) {
        this.accountSubType = accountSubType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DanskeAccount{" +
                "account=" + Arrays.toString(account) +
                ", currency='" + currency + '\'' +
                ", accountType='" + accountType + '\'' +
                ", accountSubType='" + accountSubType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
