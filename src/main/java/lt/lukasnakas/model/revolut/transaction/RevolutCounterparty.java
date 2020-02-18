package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutCounterparty {

    @JsonProperty("account_type")
    private String accountType;
    @JsonProperty("account_id")
    private String accountId;

    public RevolutCounterparty() {
    }

    public RevolutCounterparty(String accountType, String accountId) {
        this.accountType = accountType;
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
