package lt.lukasnakas.model.revolut;

import com.fasterxml.jackson.annotation.JsonAlias;
import lt.lukasnakas.model.AccessToken;

public class RevolutAccessToken extends AccessToken {

	@JsonAlias("token_type")
	private String tokenType;
	@JsonAlias("expires_in")
	private String expiresIn;

	public RevolutAccessToken() {
	}

	public RevolutAccessToken(String tokenType, String expiresIn) {
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
	}

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
