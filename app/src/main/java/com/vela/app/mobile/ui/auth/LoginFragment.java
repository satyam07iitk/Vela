package com.vela.app.mobile.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vela.app.mobile.R;
import com.vela.app.mobile.ui.activities.ForgetPasswordActivity;

public class LoginFragment extends AuthenticationFragment {

    private TextView buttonlogin, buttonReset;
    private Button buttonSkip;
    private Button buttonNotRegistered;
    private Button buttonForgotPassword;
    private EditText editTextEmail;
    private EditText editTextPassword;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        buttonFacebook = (FloatingActionButton) rootView.findViewById(R.id.button_login_facebook);
        buttonGoogle = (FloatingActionButton) rootView.findViewById(R.id.button_login_google);
        buttonInstagram = (FloatingActionButton) rootView.findViewById(R.id.button_login_instagram);
        buttonTwitter = (FloatingActionButton) rootView.findViewById(R.id.button_login_twitter);
        buttonlogin = (TextView) rootView.findViewById(R.id.button_login);
        buttonReset = (TextView) rootView.findViewById(R.id.reset);
//        buttonSkip = (Button) rootView.findViewById(R.id.button_skip);
        editTextEmail = (EditText) rootView.findViewById(R.id.edit_text_email);
        buttonForgotPassword = (Button) rootView.findViewById(R.id.button_forgot_password);
        buttonNotRegistered = (Button) rootView.findViewById(R.id.button_not_registered);
        editTextPassword = (EditText) rootView.findViewById(R.id.edit_text_password);

        buttonFacebook.setOnClickListener(this);
        buttonGoogle.setOnClickListener(this);
        buttonInstagram.setOnClickListener(this);
        buttonTwitter.setOnClickListener(this);
        buttonlogin.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
        buttonNotRegistered.setOnClickListener(this);
//        buttonSkip.setOnClickListener(this);
        buttonForgotPassword.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == buttonlogin) {
            doLogin();
            return;
        }
        if (v == buttonReset) {
            editTextEmail.setText("");
            editTextPassword.setText("");
            return;
        }
        if (v == buttonForgotPassword) {
            startActivity(new Intent(getActivity(),
                    ForgetPasswordActivity.class));
            return;
        }
        if (v == buttonNotRegistered) {
            ((AuthenticationActivity) getActivity()).buttonNotRegisteredClicked();
            return;
        }
    }

    private void doLogin() {
        String username = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (username == null || username.trim().length() < 5 || username.trim().length() >= 100) {
            showAlertDialog("Invalid EmailId", "EmailId is invalid. EmailId length must be between 6 to 100 characters.");
            return;
        }
        if (password == null || password.trim().length() < 5 || password.trim().length() >= 20) {
            showAlertDialog("Invalid EmailId", "Password is invalid. Password length must be between 6 to 20 characters.");
            return;
        }
        login(username, password);
    }
}
