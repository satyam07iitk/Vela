package com.vela.app.mobile.ui.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vela.app.mobile.R;
import com.vela.app.mobile.models.User;
import com.vela.app.mobile.ui.auth.AuthenticationActivity;

/**
 * Created by admin on 03-03-2016.
 */
public class SettingActivity extends Activity {
    private int splashTimeOut = 3000;
    private LinearLayout splashLayout;
    SharedPreferences file;
    SharedPreferences.Editor edit;
    RelativeLayout logout, change_pwd, change_mob, about, who, share, notification, completeProfile, howto, terms;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting1);

        file = getSharedPreferences("Casafet", 2);
        edit = file.edit();

        logout=(RelativeLayout) findViewById(R.id.logout);
        change_pwd=(RelativeLayout) findViewById(R.id.change_pwd);
        change_mob=(RelativeLayout) findViewById(R.id.change_mob);
        who=(RelativeLayout) findViewById(R.id.who);
        share=(RelativeLayout) findViewById(R.id.share);
        notification=(RelativeLayout) findViewById(R.id.notification);
        completeProfile=(RelativeLayout) findViewById(R.id.comp_prof);
        about=(RelativeLayout) findViewById(R.id.about);
        howto=(RelativeLayout) findViewById(R.id.howto);
        terms=(RelativeLayout) findViewById(R.id.terms);
        btnBack=(ImageView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(SettingActivity.this,
//                        HomeMainActivity.class));

                finish();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmAlertDialouge();

//               finish();
            }
        });

        file = getSharedPreferences("Casafet", 2);
        edit = file.edit();


    }
    private void showLogoutConfirmAlertDialouge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Logout !");
        builder.setMessage("Are you sure, you want to Logout?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        User.removeCurrentUser();
                        Intent intent = new Intent(SettingActivity.this,AuthenticationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                                finish();


                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        startActivity(new Intent(SettingActivity.this,
//                HomeMainActivity.class));

        finish();
    }
}
