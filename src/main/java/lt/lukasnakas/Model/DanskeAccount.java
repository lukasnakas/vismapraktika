package lt.lukasnakas.Model;

import java.util.List;

public class DanskeAccount {

    private String id;
    private DanskeAccountDetails[] Account;

    public DanskeAccount() {
    }

    public DanskeAccount(String id, DanskeAccountDetails[] account) {
        this.id = id;
        Account = account;
    }

    @Override
    public String toString() {
        return "DanskeCustomer{" +
                "id='" + id + '\'' +
                ", Account=" + Account +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DanskeAccountDetails[] getAccount() {
        return Account;
    }

    public void setAccount(DanskeAccountDetails[] account) {
        Account = account;
    }
}
