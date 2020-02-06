package lt.lukasnakas.Model.Danske;

import lt.lukasnakas.Model.Account;

public class DanskeAccount extends Account {

    private DanskeAccountDetails[] Account;

    public DanskeAccount(DanskeAccountDetails[] account) {
        Account = account;
    }

    public DanskeAccount(String id, DanskeAccountDetails[] account) {
        super(id);
        Account = account;
    }

    public DanskeAccountDetails[] getAccount() {
        return Account;
    }

    public void setAccount(DanskeAccountDetails[] account) {
        Account = account;
    }
}
