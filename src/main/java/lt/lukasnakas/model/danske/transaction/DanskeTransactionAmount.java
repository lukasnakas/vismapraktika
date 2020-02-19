package lt.lukasnakas.model.danske.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DanskeTransactionAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @JsonProperty("Amount")
    private double amount;
    @JsonProperty("Currency")
    private String currency;

    @OneToOne(mappedBy = "transactionAmount")
    private DanskeTransaction danskeTransaction;

    public DanskeTransactionAmount() {
    }

    public DanskeTransactionAmount(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
