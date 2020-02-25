package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RevolutReceiver)) {
            return false;
        }

        RevolutReceiver revolutReceiver = (RevolutReceiver) obj;

        return Objects.equals(counterPartyId, revolutReceiver.counterPartyId) &&
                Objects.equals(accountId, revolutReceiver.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(counterPartyId, accountId);
    }
}
