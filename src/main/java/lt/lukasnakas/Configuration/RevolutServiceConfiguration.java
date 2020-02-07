package lt.lukasnakas.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "revolut-service")
public class RevolutServiceConfiguration {

    @NotNull private String name;
    @NotNull private String tokenType;
    @NotNull private String urlAuth;
    @NotNull private String urlAccounts;
    @NotNull private String clientId;
    @NotNull private String grantType;
    @NotNull private String accessToken;
    @NotNull private String refreshToken;
    @NotNull private String clientAssertionType;
    @NotNull private String clientAssertion;
    @NotNull private String authorizationError;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUrlAuth() {
        return urlAuth;
    }

    public void setUrlAuth(String urlAuth) {
        this.urlAuth = urlAuth;
    }

    public String getUrlAccounts() {
        return urlAccounts;
    }

    public void setUrlAccounts(String urlAccounts) {
        this.urlAccounts = urlAccounts;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getClientAssertionType() {
        return clientAssertionType;
    }

    public void setClientAssertionType(String clientAssertionType) {
        this.clientAssertionType = clientAssertionType;
    }

    public String getClientAssertion() {
        return clientAssertion;
    }

    public void setClientAssertion(String clientAssertion) {
        this.clientAssertion = clientAssertion;
    }

    public String getAuthorizationError() {
        return authorizationError;
    }

    public void setAuthorizationError(String authorizationError) {
        this.authorizationError = authorizationError;
    }
}
