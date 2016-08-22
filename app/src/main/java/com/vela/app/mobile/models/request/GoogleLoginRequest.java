package com.vela.app.mobile.models.request;

public class GoogleLoginRequest extends BaseLoginRequest {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
