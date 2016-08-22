package com.vela.app.mobile.api;

public class UrlConstants {

    public static final String BASE_URL = "http://casafet.com/casafet/api/v1/";

    private static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getAddUserUrl() {
        return getBaseUrl() + "user";
    }

    public static String getLoginUrl() {
        return getBaseUrl() + "login";
    }

    public static String getLoginWIthGoogleUrl() {
        return getBaseUrl() + "loginGoogle";
    }

    public static String getLoginWithTwitterUrl() {
        return getBaseUrl() + "loginTwitterVela";
    }

    public static String getLoginWithFacebookUrl() {
        return getBaseUrl() + "loginFacebook";
    }
}
