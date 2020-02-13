package lt.lukasnakas.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessToken {

	@JsonProperty("accessToken")
	@JsonAlias("access_token")
	private String token;

	public AccessToken() {
	}

	public AccessToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
