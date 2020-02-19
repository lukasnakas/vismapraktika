package lt.lukasnakas.model.revolut.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Transaction;

import javax.persistence.Entity;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RevolutTransaction extends Transaction {

    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("completed_at")
    private String completedAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("type")
    private String type;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("legs")
    private RevolutTransactionLegs[] legs;
    @JsonProperty("state")
    private String state;

    public RevolutTransaction() {
    }

    public RevolutTransaction(String id, String type, String requestId, String updatedAt, String reference,
                              RevolutTransactionLegs[] legs, String state, String createdAt, String completedAt) {
        super(id);
        this.type = type;
        this.requestId = requestId;
        this.updatedAt = updatedAt;
        this.reference = reference;
        this.legs = legs;
        this.state = state;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public RevolutTransactionLegs[] getLegs() {
        return legs;
    }

    public void setLegs(RevolutTransactionLegs[] legs) {
        this.legs = legs;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}
