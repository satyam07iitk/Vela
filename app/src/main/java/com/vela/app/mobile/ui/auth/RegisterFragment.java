package com.vela.app.mobile.ui.auth;

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

public class RegisterFragment extends AuthenticationFragment {

    private TextView buttonRegister, btnReset;
//    private ImageButton buttonDriverHelp;
    private Button buttonAlreadyRegistered;
    private EditText editTextEmail;
    private EditText editTextPassword;
//    private CheckBox checkBoxDriver;
    private EditText editTextConfirmPassword;

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        buttonFacebook = (FloatingActionButton) rootView.findViewById(R.id.button_login_facebook);
        buttonGoogle = (FloatingActionButton) rootView.findViewById(R.id.button_login_google);
        buttonInstagram = (FloatingActionButton) rootView.findViewById(R.id.button_login_instagram);
        buttonTwitter = (FloatingActionButton) rootView.findViewById(R.id.button_login_twitter);
        buttonRegister = (TextView) rootView.findViewById(R.id.button_register);
        btnReset = (TextView) rootView.findViewById(R.id.reset);
        buttonAlreadyRegistered = (Button) rootView.findViewById(R.id.button_already_registered);
//        buttonDriverHelp = (ImageButton) rootView.findViewById(R.id.button_driver_help);
        editTextEmail = (EditText) rootView.findViewById(R.id.edit_text_email);
        editTextConfirmPassword = (EditText) rootView.findViewById(R.id.edit_text_confirm_password);
        editTextPassword = (EditText) rootView.findViewById(R.id.edit_text_password);
//        checkBoxDriver = (CheckBox) rootView.findViewById(R.id.checkbox_driver);

        buttonFacebook.setOnClickListener(this);
        buttonGoogle.setOnClickListener(this);
        buttonInstagram.setOnClickListener(this);
        buttonTwitter.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        buttonAlreadyRegistered.setOnClickListener(this);
//        buttonDriverHelp.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == buttonRegister) {
            doRegister();
            return;
        }
        if (v == btnReset) {
            editTextEmail.setText("");
            editTextPassword.setText("");
            editTextConfirmPassword.setText("");
            return;
        }
        if (v == buttonAlreadyRegistered) {
            ((AuthenticationActivity) getActivity()).buttonAlreadyRegisteredClicked();
            return;
        }
//        if (v == buttonDriverHelp) {
//            return;
//        }
    }

    private void doRegister() {
        String username = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        if (username == null || username.trim().length() < 5 || username.trim().length() >= 100) {
            showAlertDialog("Invalid EmailId", "EmailId is invalid. EmailId length must be between 6 to 100 characters.");
            return;
        }
        if (password == null || password.trim().length() < 5 || password.trim().length() >= 20) {
            showAlertDialog("Invalid Password", "Password is invalid. Password length must be between 6 to 20 characters.");
            return;
        }
        if (confirmPassword == null || !password.equals(confirmPassword)) {
            showAlertDialog("Password and Confirm Password not matched", "Password and Confirm password must be same.");
            return;
        }
        registerUser(username, password);
    }
}
