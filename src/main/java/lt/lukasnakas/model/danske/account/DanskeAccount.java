package lt.lukasnakas.model.danske.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.Account;

public class DanskeAccount extends Account {

    @JsonProperty("Data")
    private Data data;

    public DanskeAccount() {
    }

    public DanskeAccount(String id, Data data) {
        super(id);
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
