package com.vela.app.mobile.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.vela.app.mobile.models.User;

public class AppSharedPreferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public AppSharedPreferences() {
        this.context = CSApplication.getInstance().getApplicationContext();
        sharedPreferences = context.getSharedPreferences("Vela", Context.MODE_PRIVATE);
    }

    public AppSharedPreferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Vela", Context.MODE_PRIVATE);
    }

    public User getUser() {
        User user = new User();
        user.setAccessToken(sharedPreferences.getString("accessToken", null));
        user.setName(sharedPreferences.getString("name", null));
        user.setEmailId(sharedPreferences.getString("emailId", null));
        user.setUserId(sharedPreferences.getString("userId", null));
        return user;
    }

    public void setUser(User user) {
        this.editor = sharedPreferences.edit();
        this.editor.putString("name", user.getName());
        this.editor.putString("accessToken", user.getAccessToken());
        this.editor.putString("emailId", user.getEmailId());
        this.editor.putString("userId", user.getUserId());
        this.editor.commit();
    }

    public void removeUser() {
        this.editor = sharedPreferences.edit();
        this.editor.remove("name");
        this.editor.remove("accessToken");
        this.editor.remove("emailId");
        this.editor.remove("userId");
        this.editor.commit();
    }

    public String getGCMRegistrationId() {
        return this.sharedPreferences.getString("registration_id", null);
    }

    public void setGCMRegistrationId(String regId) {
        this.editor = this.sharedPreferences.edit();
        this.editor.putString("registration_id", regId);
        this.editor.commit();
    }

    public int getAppRegisteredVersion() {
        return this.sharedPreferences.getInt("APP_VERSION", 0);
    }

    public void setAppRegisteredVersion(int version) {
        this.editor = this.sharedPreferences.edit();
        this.editor.putInt("APP_VERSION", version);
        this.editor.commit();
    }
}
