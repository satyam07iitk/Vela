package com.vela.app.mobile.models.request;

public class TwitterLoginRequest extends BaseLoginRequest {

    private String token;
    private String tokenSecret;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

}
