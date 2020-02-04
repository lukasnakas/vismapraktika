package lt.lukasnakas.Model;

public class Bank {
    private int id;
    private String name;
    private String accessToken;
    private String URL;

    public Bank() {
    }

    public Bank(int id, String name, String accessToken, String URL) {
        this.id = id;
        this.name = name;
        this.accessToken = accessToken;
        this.URL = URL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
