package lt.lukasnakas.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AccessToken {

    @JsonAlias({"access_token", "accessToken"})
    private String token;

    public AccessToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
