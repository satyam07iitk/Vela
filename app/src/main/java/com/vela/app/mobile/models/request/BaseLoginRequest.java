package com.vela.app.mobile.models.request;

import com.vela.app.mobile.app.AppSharedPreferences;

public class BaseLoginRequest {

    public static final int DEVICE_TYPE_ANDROID = 2;

    protected String username;
    protected int deviceType;
    protected String pushNotificationId;

    public BaseLoginRequest() {
        deviceType = DEVICE_TYPE_ANDROID;
        pushNotificationId = new AppSharedPreferences().getGCMRegistrationId();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public String getPushNotificationId() {
        return pushNotificationId;
    }

}
