package lt.lukasnakas.model.danske.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Transaction;

public class DanskeTransaction extends Transaction {

    @JsonProperty("AccountId")
    private String accountId;

    @JsonProperty("CreditDebitIndicator")
    private String creditDebitIndicator;

    @JsonProperty("Amount")
    private DanskeTransactionAmount transactionAmount;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCreditDebitIndicator() {
        return creditDebitIndicator;
    }

    public void setCreditDebitIndicator(String creditDebitIndicator) {
        this.creditDebitIndicator = creditDebitIndicator;
    }

    public DanskeTransactionAmount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(DanskeTransactionAmount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
