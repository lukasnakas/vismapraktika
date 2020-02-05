package lt.lukasnakas.Model;

public class DanskeAccount {

    private String Identification;
    private String SchemeName;
    private String Name;
    private String SecondaryIdentification;

    public DanskeAccount() {
    }

    public DanskeAccount(String identification, String schemeName, String name, String secondaryIdentification) {
        Identification = identification;
        SchemeName = schemeName;
        Name = name;
        SecondaryIdentification = secondaryIdentification;
    }

    public String getIdentification() {
        return Identification;
    }

    public void setIdentification(String identification) {
        Identification = identification;
    }

    public String getSchemeName() {
        return SchemeName;
    }

    public void setSchemeName(String schemeName) {
        SchemeName = schemeName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSecondaryIdentification() {
        return SecondaryIdentification;
    }

    public void setSecondaryIdentification(String secondaryIdentification) {
        SecondaryIdentification = secondaryIdentification;
    }
}
