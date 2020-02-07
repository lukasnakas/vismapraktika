package lt.lukasnakas.model.danske;

import com.google.gson.annotations.SerializedName;
import lt.lukasnakas.model.Account;

public class DanskeAccount extends Account {

    @SerializedName("Account")
    private DanskeAccountDetails[] account;

    public DanskeAccount(DanskeAccountDetails[] account) {
        this.account = account;
    }

    public DanskeAccount(String id, DanskeAccountDetails[] account) {
        super(id);
        this.account = account;
    }

    public DanskeAccountDetails[] getAccount() {
        return account;
    }

    public void setAccount(DanskeAccountDetails[] account) {
        this.account = account;
    }
}
