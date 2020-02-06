package lt.lukasnakas.Model.Danske;

public class DanskeAccessToken {

    private String accessToken;

    public DanskeAccessToken() {
    }

    public DanskeAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
