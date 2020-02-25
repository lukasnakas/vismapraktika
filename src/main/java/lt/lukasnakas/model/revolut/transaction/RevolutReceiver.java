package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutReceiver {

    @JsonProperty("counterparty_id")
    private String counterPartyId;
    @JsonProperty("account_id")
    private String accountId;

    public RevolutReceiver() {
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
