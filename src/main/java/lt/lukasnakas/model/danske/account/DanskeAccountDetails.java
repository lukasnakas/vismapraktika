package lt.lukasnakas.model.danske.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class DanskeAccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @JsonProperty("Identification")
    private String identification;
    @JsonProperty("SchemeName")
    private String schemeName;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("SecondaryIdentification")
    private String secondaryIdentification;
    @ManyToOne
    @JoinColumn(name = "danskeAccount_id", nullable = false)
    private DanskeAccount danskeAccount;

    public DanskeAccountDetails() {
    }

    public DanskeAccountDetails(String identification, String schemeName, String name, String secondaryIdentification) {
        this.identification = identification;
        this.schemeName = schemeName;
        this.name = name;
        this.secondaryIdentification = secondaryIdentification;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DanskeAccount getDanskeAccount() {
        return danskeAccount;
    }

    public void setDanskeAccount(DanskeAccount danskeAccount) {
        this.danskeAccount = danskeAccount;
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

    @Override
    public String toString() {
        return "DanskeAccountDetails{" +
                "id=" + id +
                ", identification='" + identification + '\'' +
                ", schemeName='" + schemeName + '\'' +
                ", name='" + name + '\'' +
                ", secondaryIdentification='" + secondaryIdentification + '\'' +
                ", danskeAccount=" + danskeAccount +
                '}';
    }
}
