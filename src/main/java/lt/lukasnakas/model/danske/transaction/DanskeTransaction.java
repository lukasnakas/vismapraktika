package lt.lukasnakas.model.danske.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Transaction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class DanskeTransaction extends Transaction {

    @JsonProperty("AccountId")
    private String accountId;

    @JsonProperty("CreditDebitIndicator")
    private String creditDebitIndicator;

    @JsonProperty("Amount")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_amount_id", referencedColumnName = "id")
    private DanskeTransactionAmount transactionAmount;

    public DanskeTransaction() {
    }

    public DanskeTransaction(String id, String accountId, String creditDebitIndicator, DanskeTransactionAmount transactionAmount) {
        super(id);
        this.accountId = accountId;
        this.creditDebitIndicator = creditDebitIndicator;
        this.transactionAmount = transactionAmount;
    }

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
