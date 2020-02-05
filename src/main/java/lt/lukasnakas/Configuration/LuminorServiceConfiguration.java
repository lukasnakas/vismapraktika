package lt.lukasnakas.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "luminor-service")
public class LuminorServiceConfiguration {

    @NotNull private String name;
    @NotNull private String tokenType;
    @NotNull private String urlAuth;
    @NotNull private String urlAuthAccessToken;
    @NotNull private String urlAccounts;
    @NotNull private String clientId;
    @NotNull private String clientSecret;
    @NotNull private String accessToken;
    @NotNull private String redirectUri;
    @NotNull private String fpgurl;
    @NotNull private String bypass;
    @NotNull private String bankCountry;
    @NotNull private String realm;
    @NotNull private String service;
    @NotNull private String locale;
    @NotNull private String responseType;
    @NotNull private String infLogoLabel;
    @NotNull private String authMethodReference;
    @NotNull private String scope;
    @NotNull private String authCode;
    @NotNull private String grantType;

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

    public String getUrlAuthAccessToken() {
        return urlAuthAccessToken;
    }

    public void setUrlAuthAccessToken(String urlAuthAccessToken) {
        this.urlAuthAccessToken = urlAuthAccessToken;
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

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getFpgurl() {
        return fpgurl;
    }

    public void setFpgurl(String fpgurl) {
        this.fpgurl = fpgurl;
    }

    public String getBypass() {
        return bypass;
    }

    public void setBypass(String bypass) {
        this.bypass = bypass;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    public void setBankCountry(String bankCountry) {
        this.bankCountry = bankCountry;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getInfLogoLabel() {
        return infLogoLabel;
    }

    public void setInfLogoLabel(String infLogoLabel) {
        this.infLogoLabel = infLogoLabel;
    }

    public String getAuthMethodReference() {
        return authMethodReference;
    }

    public void setAuthMethodReference(String authMethodReference) {
        this.authMethodReference = authMethodReference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
