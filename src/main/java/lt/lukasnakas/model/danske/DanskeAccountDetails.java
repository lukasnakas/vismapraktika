package lt.lukasnakas.model.danske;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DanskeAccountDetails {

    @JsonProperty("Identification")
    private String identification;
    @JsonProperty("SchemeName")
    private String schemeName;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("SecondaryIdentification")
    private String secondaryIdentification;

    public DanskeAccountDetails() {
    }

    public DanskeAccountDetails(String identification, String schemeName, String name, String secondaryIdentification) {
        this.identification = identification;
        this.schemeName = schemeName;
        this.name = name;
        this.secondaryIdentification = secondaryIdentification;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondaryIdentification() {
        return secondaryIdentification;
    }

    public void setSecondaryIdentification(String secondaryIdentification) {
        this.secondaryIdentification = secondaryIdentification;
    }
}
