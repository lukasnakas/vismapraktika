package lt.lukasnakas.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;
import lt.lukasnakas.model.AccessToken;

public class RevolutAccessToken extends AccessToken {

    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private String expiresIn;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
