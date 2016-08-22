package com.vela.app.mobile.ui.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.vela.app.mobile.R;
import com.vela.app.mobile.app.CSApplication;
import com.vela.app.mobile.ui.base.ConnectionDetector;

/**
 * Created by admin on 03-03-2016.
 */
public class ForgetPasswordActivity extends Activity {

    SharedPreferences file = null;
    Activity activity;
    ConnectionDetector cd;

    Button updatePassword;
    String errorMsg;
    SharedPreferences.Editor edit;

ImageView btnBack;



    EditText txtUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgot_password);
        cd=new ConnectionDetector(this);
        btnBack=(ImageView) findViewById(R.id.btnBack);

        btnBack=(ImageView) findViewById(R.id.btnBack);

        txtUserEmail=(EditText) findViewById(R.id.txtUserEmail);

        updatePassword=(Button) findViewById(R.id.updatePassword);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* startActivity(new Intent(ForgetPasswordActivity.this,
                        MainActivity.class));
*/
                finish();

            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (checkInputAvailable()) {
                    //   hideSoftKeyboard(this);
                    if (!cd.isConnectingToInternet()) {



                        CSApplication.popErrorMsg("Vela", "No internet connection found!", activity);



                        return;
                    } else {
                    }
                } else {

                    CSApplication.popErrorMsg("Vela", errorMsg, activity);


                }
            }
        });

    }
    private boolean checkInputAvailable() {

        boolean retVal = true;



        String txtUsernameStr = txtUserEmail.getText().toString().trim();

        if (TextUtils.isEmpty(txtUsernameStr)) {
            errorMsg = "Please enter Email Address";
            retVal = false;
        }

        /*else if (!isValidEmail(txtUsernameStr)) {
            errorMsg = "userId in valid";
            retVal = false;
        }*/
        return retVal;

    }

}

