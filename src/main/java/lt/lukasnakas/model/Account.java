package lt.lukasnakas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Account {

    @JsonProperty("id")
    private String id;

    public Account() {
    }

    public Account(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
