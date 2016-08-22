package com.vela.app.mobile.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.vela.app.mobile.R;
import com.vela.app.mobile.app.Constants;
import com.vela.app.mobile.app.Logger;
import com.vela.app.mobile.models.User;
import com.vela.app.mobile.app.CSApplication;
import com.vela.app.mobile.app.AppSharedPreferences;
import com.vela.app.mobile.ui.auth.AuthenticationActivity;
import com.vela.app.mobile.ui.home.HomeActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private WaitAsyncTask waitAsyncTask;
    private long timeStamp = System.currentTimeMillis();

    private GoogleCloudMessaging googleCloudMessaging;

    private String gcmRegistrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        this.setupFabric();
        this.setupGCM();

        // method to find hash key
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        this.handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Logger.log(Log.DEBUG, "DEBUG::SplashActivity", "handleIntent");
        // display activity base on notification data
        // launch further activities
        waitAsyncTask = new WaitAsyncTask();
        waitAsyncTask.execute();
    }

    private void setupGCM() {
        if (checkPlayServices()) {
            gcmRegistrationId = getRegistrationId();
            if (gcmRegistrationId == null || gcmRegistrationId.isEmpty()) {
                registerInBackground();
            }
        } else {
            Logger.log(Log.INFO, "DEBUG::SplashActivity", "No valid Google Play Services APK found.");
        }
    }

    // TODO: remove deprecated code
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Logger.log(Log.INFO, "DEBUG::SplashActivity", "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param regId registration ID
     */
    private void storeRegistrationId(String regId) {
        AppSharedPreferences sharedPref = new AppSharedPreferences(this);
        sharedPref.setGCMRegistrationId(regId);
        sharedPref.setAppRegisteredVersion(getAppVersion());
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId() {
        AppSharedPreferences sharedPref = new AppSharedPreferences(this);
        int registeredVersion = sharedPref.getAppRegisteredVersion();
        int currentVersion = getAppVersion();
        if (registeredVersion != currentVersion) {
            Logger.log(Log.DEBUG, "DEBUG::SplashActivity", "App version changed");
            return null;
        }

        String regId = sharedPref.getGCMRegistrationId();
        return regId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (googleCloudMessaging == null) {
                        googleCloudMessaging = GoogleCloudMessaging.getInstance(CSApplication.getInstance().getApplicationContext());
                    }
                    gcmRegistrationId = googleCloudMessaging.register(Constants.GCM_SENDER_ID);
                    msg = "Device registered, registration ID=" + gcmRegistrationId;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(gcmRegistrationId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Logger.log(Log.DEBUG, "DEBUG::SplashActivity", msg);
            }
        }.execute(null, null, null);
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion() {
        try {
            Context context = CSApplication.getInstance().getApplicationContext();
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend() {
        // TODO if user is already logged in update registration id with this current accessToken, else it will be send to server during login process
    }

    @Override
    protected void onDestroy() {
        if (this.waitAsyncTask != null) {
            this.waitAsyncTask.cancel(true);
        }
        super.onDestroy();
    }

    private void invokeActivity() {
        Class<? extends AppCompatActivity> activityToLaunchClass = null;
        User user = User.getCurrentUser();
        if (user.getAccessToken() != null && user.getAccessToken().length() > 0) {
            activityToLaunchClass = HomeActivity.class;
        } else {
            activityToLaunchClass = AuthenticationActivity.class;
        }
        Intent intent = new Intent(SplashActivity.this, activityToLaunchClass);
        startActivity(intent);
        this.finish();
    }


    private class WaitAsyncTask extends AsyncTask<Void, Void, Void> {

        public WaitAsyncTask() {
            super();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                long sleepTime = System.currentTimeMillis() - timeStamp;
                sleepTime = Math.min(Math.max(0, 2000 - sleepTime), 2000);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!isCancelled()) {
                invokeActivity();
            }
        }
    }
}
