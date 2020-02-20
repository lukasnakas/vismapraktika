package lt.lukasnakas.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "danske-service")
public class DanskeServiceConfiguration {

    @NotNull
    private String name;
    @NotNull
    private String tokenType;
    @NotNull
    private String xFapiFinancialId;
    @NotNull
    private String urlAccountsVirtual;
    @NotNull
    private String urlTransactionsVirtual;
    @NotNull
    private String accessTokenVirtual;
    @NotNull
    private String urlAuth;
    @NotNull
    private String urlAccounts;
    @NotNull
    private String urlAccountTransactions;
    @NotNull
    private String clientId;
    @NotNull
    private String accessToken;
    @NotNull
    private String secret;

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

    public String getxFapiFinancialId() {
        return xFapiFinancialId;
    }

    public void setxFapiFinancialId(String xFapiFinancialId) {
        this.xFapiFinancialId = xFapiFinancialId;
    }

    public String getUrlAccountsVirtual() {
        return urlAccountsVirtual;
    }

    public void setUrlAccountsVirtual(String urlAccountsVirtual) {
        this.urlAccountsVirtual = urlAccountsVirtual;
    }

    public String getUrlTransactionsVirtual() {
        return urlTransactionsVirtual;
    }

    public void setUrlTransactionsVirtual(String urlTransactionsVirtual) {
        this.urlTransactionsVirtual = urlTransactionsVirtual;
    }

    public String getAccessTokenVirtual() {
        return accessTokenVirtual;
    }

    public void setAccessTokenVirtual(String accessTokenVirtual) {
        this.accessTokenVirtual = accessTokenVirtual;
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

    public String getUrlAccountTransactions() {
        return urlAccountTransactions;
    }

    public void setUrlAccountTransactions(String urlAccountTransactions) {
        this.urlAccountTransactions = urlAccountTransactions;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
