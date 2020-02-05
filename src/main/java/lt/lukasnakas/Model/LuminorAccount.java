package lt.lukasnakas.Model;

public class LuminorAccount {

    private String accountId;
    private String iban;
    private String currency;
    private boolean cardAccount;
    private String bic;
    private String usage;
    private double[] balances;

    public LuminorAccount() {
    }

    public LuminorAccount(String accountId, String iban, String currency, boolean cardAccount, String bic, String usage, double[] balances) {
        this.accountId = accountId;
        this.iban = iban;
        this.currency = currency;
        this.cardAccount = cardAccount;
        this.bic = bic;
        this.usage = usage;
        this.balances = balances;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(boolean cardAccount) {
        this.cardAccount = cardAccount;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public double[] getBalances() {
        return balances;
    }

    public void setBalances(double[] balances) {
        this.balances = balances;
    }
}
