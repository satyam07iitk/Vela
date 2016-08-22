package com.vela.app.mobile.ui.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.vela.app.mobile.ui.base.ConnectionDetector;
import com.vela.app.mobile.api.ApiResponseHandler;
import com.vela.app.mobile.api.ApiTask;
import com.vela.app.mobile.api.UrlConstants;
import com.vela.app.mobile.api.VolleyApiTask;
import com.vela.app.mobile.app.CSError;
import com.vela.app.mobile.models.User;
import com.vela.app.mobile.models.request.FacebookLoginRequest;
import com.vela.app.mobile.models.request.GoogleLoginRequest;
import com.vela.app.mobile.models.request.LoginRequest;
import com.vela.app.mobile.models.request.RegisterRequest;
import com.vela.app.mobile.models.request.TwitterLoginRequest;
import com.vela.app.mobile.ui.home.HomeActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import io.fabric.sdk.android.Fabric;

public abstract class AuthenticationFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private final String GOOGLE_REQUEST_ID_TOKEN = "434677133107-57iv6o88thqua809eus08644p83p8hbo.apps.googleusercontent.com";

    private String googleEmail;

    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";

    private ConnectionResult mConnectionResult;
    private CallbackManager callbackManager;
    private GoogleSignInOptions googleSignInOptions;
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "I9otIGV48Fz6Tcq5b8r2CLEfz";
    private static final String TWITTER_SECRET = "DrSvrJZNZNfDjh4FHJABnpyjtF7GteiVWs2alABShZvaOX02f5";
    //private TwitterLoginButton loginButton;
    ConnectionDetector cd;
    private TwitterAuthClient twitterAuthClient;

    protected FloatingActionButton buttonFacebook;
    protected FloatingActionButton buttonGoogle;
    protected FloatingActionButton buttonTwitter;
    protected FloatingActionButton buttonInstagram;

    protected ProgressDialog progressDialog;

    private GoogleApiClient googleApiClient;


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonFacebook) {
            doSignInWithFacebook();
            return;
        }
        if (v == buttonGoogle) {
            doSignInWithGoogle();
            return;
        }
        if (v == buttonTwitter) {
            doSignInWithTwitter();
            return;
        }
        if (v == buttonInstagram) {

            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
            return;
        }
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (twitterAuthClient != null) {
            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    protected void loginSuccess(User user) {
        if (getActivity() != null) {
            hideLoading();
            User.setCurrentUser(user);
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            this.getActivity().finish();
        }
    }

    protected void loginFailure(CSError apiError) {
        if (getActivity() != null) {
            hideLoading();
            showAlertDialog("Error", apiError.getMesssage());
        }
    }

    protected void showLoading(String message) {
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setTitle(null);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    protected void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected void showAlertDialog(String title, String message) {
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton("Ok", null);
            builder.show();
        }
    }

    protected void registerUser(final String username, final String password) {
        showLoading("Registering user...");
        ApiTask<User> apiTask = new VolleyApiTask<User>(this.getContext(), UrlConstants.getAddUserUrl(), ApiTask.APIMethod.POST, User.class, new ApiResponseHandler<User>() {
            @Override
            public void onApiSuccess(User response) {
                super.onApiSuccess(response);
                hideLoading();
                Toast.makeText(getContext(), "User Registration Success", Toast.LENGTH_SHORT).show();
                login(username, password);
            }

            @Override
            public void onApiFailure(CSError error) {
                super.onApiFailure(error);
                loginFailure(error);
            }
        });
        RegisterRequest request = new RegisterRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setConfirmPassword(password);
        apiTask.executeRequest(request);
    }

    protected void login(String username, String password) {
        showLoading("Logging in user...");
        ApiTask<User> apiTask = new VolleyApiTask<User>(this.getContext(), UrlConstants.getLoginUrl(), ApiTask.APIMethod.POST, User.class, new ApiResponseHandler<User>() {
            @Override
            public void onApiSuccess(User response) {
                super.onApiSuccess(response);
                loginSuccess(response);
            }

            @Override
            public void onApiFailure(CSError error) {
                super.onApiFailure(error);
                loginFailure(error);
            }
        });
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        apiTask.executeRequest(request);
    }

    private void doSignInWithGoogle() {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().requestIdToken(GOOGLE_REQUEST_ID_TOKEN).build();
        googleApiClient = new GoogleApiClient.Builder(getActivity()).enableAutoManage(getActivity(), this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
        if (googleApiClient != null) {
            showLoading("Signing in using Google...");
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            // show dialog
            showAlertDialog("Error", "You need to either install or update Google Play Services to sign in using Google.");
        }
    }

    private void handleGoogleSignInResult(final GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            googleEmail = acct.getEmail();
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            String idToken = acct.getIdToken();

            if (TextUtils.isEmpty(googleEmail)) {
                showAlertDialog("Google SignIn Error", "Did not get sufficient information to login. It may be due to privacy setting in your Google account. You may change privacy for accessing profile details in your Google account and try again");
                hideLoading();
            }
            ApiTask<User> apiTask = new VolleyApiTask<User>(this.getContext(), UrlConstants.getLoginWIthGoogleUrl(), ApiTask.APIMethod.POST, User.class, new ApiResponseHandler<User>() {
                @Override
                public void onApiSuccess(User response) {
                    super.onApiSuccess(response);
                    loginSuccess(response);
                }

                @Override
                public void onApiFailure(CSError error) {
                    super.onApiFailure(error);
                    loginFailure(error);
                }
            });
            GoogleLoginRequest request = new GoogleLoginRequest();
            request.setUsername(personEmail);
            request.setToken(idToken);
            apiTask.executeRequest(request);
        } else {
            showAlertDialog("Google SignIn Error", "Unable to sign in using Google, please try again.");
            hideLoading();
        }
    }

    private void doSignInWithTwitter() {
        showLoading("Signing in using Twitter...");
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this.getContext(), new Twitter(authConfig));
        twitterAuthClient = new TwitterAuthClient();
        twitterAuthClient.authorize(getActivity(), new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                String name = session.getUserName();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                User response = new User();
                response.setName(name);
                response.setAccessToken("access_token");
                loginSuccess(response);
                // make api call to our server
//                ApiTask<User> apiTask = new VolleyApiTask<User>(AuthenticationFragment.this.getContext(), UrlConstants.getLoginWithTwitterUrl(), ApiTask.APIMethod.POST, User.class, new ApiResponseHandler<User>() {
//                    @Override
//                    public void onApiSuccess(User response) {
//                        super.onApiSuccess(response);
//                        loginSuccess(response);
//                    }
//
//                    @Override
//                    public void onApiFailure(CSError error) {
//                        super.onApiFailure(error);
//                        loginFailure(error);
//                    }
//                });
//                TwitterLoginRequest request = new TwitterLoginRequest();
//                request.setUsername(name);
//                request.setToken(token);
//                request.setTokenSecret(secret);
//                apiTask.executeRequest(request);
            }

            @Override
            public void failure(TwitterException exception) {
                hideLoading();
                showAlertDialog("Twitter SignIn Error", "Unable to sign in with Twitter, please try again.");
            }
        });
    }

    private void doSignInWithFacebook() {
        showLoading("Signing in using Facebook...");
        FacebookSdk.sdkInitialize(this.getActivity());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                final String token = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                    if (object != null) {
                        try {
                            // make api call to our server
                            ApiTask<User> apiTask = new VolleyApiTask<User>(AuthenticationFragment.this.getContext(), UrlConstants.getLoginWithFacebookUrl(), ApiTask.APIMethod.POST, User.class, new ApiResponseHandler<User>() {
                                @Override
                                public void onApiSuccess(User response) {
                                    super.onApiSuccess(response);
                                    loginSuccess(response);
                                }

                                @Override
                                public void onApiFailure(CSError error) {
                                    super.onApiFailure(error);
                                    loginFailure(error);
                                }
                            });
                            FacebookLoginRequest request = new FacebookLoginRequest();
                            request.setUsername(object.getString("email"));
                            request.setToken(token);
                            request.setId(object.getString("id"));
                            apiTask.executeRequest(request);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideLoading();
                            showAlertDialog("Facebook SignIn Error", "Unable to sign in with Facebook, please try again.");
                        }
                    } else {
                        hideLoading();
                        showAlertDialog("Facebook SignIn Error", "Unable to sign in with Facebook, please try again.");
                    }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture,link");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                hideLoading();
                showAlertDialog("Facebook SignIn Cancelled", "Unable to sign in with Facebook, as it is cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                hideLoading();
                showAlertDialog("Facebook SignIn Error", "Unable to sign in with Facebook, please try again.");
            }
        });
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

//    /**
//     * Method to resolve any signin errors
//     */
//    private void resolveSignInError() {
//        if (mConnectionResult.hasResolution()) {
//            try {
//                mIntentInProgress = true;
//                mConnectionResult.startResolutionForResult(getActivity(), RC_SIGN_IN);
//            } catch (IntentSender.SendIntentException e) {
//                mIntentInProgress = false;
//                googleApiClient.connect();
//            }
//        }
//    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (googleApiClient != null) {
            hideLoading();
            showAlertDialog("Google SignIn Error", "Unable to sign in with Google, please try later.");
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

//    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            String accountName = params[0];
//            String scopes = "oauth2:profile email";
//            String token = null;
//            try {
//                token = GoogleAuthUtil.getToken(getActivity().getApplicationContext(), accountName, scopes);
//            } catch (IOException e) {
//                Log.e(TAG, e.getMessage());
//            } catch (UserRecoverableAuthException e) {
//                startActivityForResult(e.getIntent(), REQ_SIGN_IN_REQUIRED);
//            } catch (GoogleAuthException e) {
//                Log.e(TAG, e.getMessage());
//            }
//            return token;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            JSONObject obj = new JSONObject();
//            try {
//
//
//                obj.put("username", googleEmail);
//                obj.put("token", s);
//
//
//                obj.put("deviceType", 2);
//                obj.put("pushNotificationId", "");
//
//
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            makeJsonObjectRequestTWITTER(obj, "loginGoogle");
//        }
//    }

//    private void makeJsonObjectRequestTWITTER(JSONObject params, String url_part) {
//
//
//        CSApplication.spinnerStart(getActivity());
//
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Const.URL_Main + url_part,
//                params, newResponseRequesr(), eErrorListenerRequesr()) {
//
//        };
//        CSApplication.getInstance().addToRequestQueue(jsonObjReq, "twiiterfacebookgoogle");
//    }
//
//    private Response.Listener<JSONObject> newResponseRequesr() {
//        Response.Listener<JSONObject> response = new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//
//                Log.e("Responce twitter", response.toString());
//
//                JSONObject jsonObject = null;
//
//                try {
//                    jsonObject = new JSONObject(response.toString());
//
//                    if (jsonObject.has("accountStatus")) {
//
//                        int accountStatus = jsonObject.getInt("accountStatus");
//                        int accountType = jsonObject.getInt("accountType");
//                        int userRole = jsonObject.getInt("userRole");
//
//
//                        // JSONObject  jsonObjectLoc = jsonObject.getJSONObject("location");
//                        //  double latitude = jsonObjectLoc.getDouble("latitude");
//                        //  double longitude = jsonObjectLoc.getInt("longitude");
//
//
//                        //  String accessToken = jsonObject.getString("accessToken");
//                        String address = jsonObject.getString("address");
//                        String emailId = jsonObject.getString("emailId");
//                        String id = jsonObject.getString("id");
//                        String imageId = jsonObject.getString("imageId");
//
//
//                        String mobileNumber = jsonObject.getString("mobileNumber");
//                        String name = jsonObject.getString("name");
//                        String username = jsonObject.getString("username");
//
////                        edit.putInt("accountStatus", accountStatus).commit();
////                        edit.putInt("accountType", accountType).commit();
////                        edit.putInt("userRole", userRole);
////
////
////                        // edit.putString("latitude",""+latitude).commit();
////                        //  edit.putString("longitude",""+longitude).commit();
////                        // edit.putString("accessToken",accessToken).commit();
////                        edit.putString("address", address).commit();
////                        edit.putString("emailId", emailId).commit();
////                        edit.putString("id", id).commit();
////                        edit.putString("imageId", imageId).commit();
////
////                        edit.putString("mobileNumber", mobileNumber).commit();
////                        edit.putString("name", name).commit();
////                        edit.putString("username", username).commit();
//
//
//                        startActivity(new Intent(getActivity(), HomeMainActivity.class));
//
//                        // TODO:
//                        getActivity().finish();
//                    } else {
//
//                        startActivity(new Intent(getActivity(), HomeMainActivity.class));
//
//                        // TODO:
//                        getActivity().finish();
//                        //CSApplication.popErrorMsg("Casafet", "User Not Exist", MainActivity.this);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                CSApplication.spinnerStop();
//
//            }
//
//        };
//        return response;
//    }
//
//    private Response.ErrorListener eErrorListenerRequesr() {
//        Response.ErrorListener response_error = new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                CSApplication.spinnerStop();
////                edit.putString("username", name).commit();
//
//
//                startActivity(new Intent(getActivity(), HomeMainActivity.class));
//
//                getActivity().finish();
//
//            }
//        };
//        return response_error;
//    }
}
