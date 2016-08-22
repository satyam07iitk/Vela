package com.vela.app.mobile.models;

import com.vela.app.mobile.app.CSApplication;
import com.vela.app.mobile.models.response.Response;
import com.vela.app.mobile.app.AppSharedPreferences;

public class User extends Response {

    private String userId;
    private String name;
    private String emailId;
    private String password;
    private String accessToken;

    private static User currentUser;

    public static User getCurrentUser() {
        if (currentUser == null) {
            currentUser = new AppSharedPreferences(CSApplication.getInstance().getApplicationContext()).getUser();
        }
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
        new AppSharedPreferences(CSApplication.getInstance().getApplicationContext()).setUser(currentUser);
    }

    public static void removeCurrentUser() {
        currentUser = null;
        new AppSharedPreferences(CSApplication.getInstance().getApplicationContext()).removeUser();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
