package com.vela.app.mobile.ui.me;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vela.app.mobile.R;
import com.vela.app.mobile.ui.auth.AuthenticationActivity;
import com.vela.app.mobile.ui.base.BaseContainerFragment;
import com.vela.app.mobile.ui.base.ConnectionDetector;
import com.vela.app.mobile.ui.settings.SettingActivity;

public class MeFragment extends BaseContainerFragment {

    private boolean IsViewInited;
    SharedPreferences file;
    SharedPreferences.Editor edit;    Activity activity;
    ConnectionDetector cd;
    ImageButton logout_imagebtn;
    private RatingBar ratingBar;
    private TextView txtRatingValue,firstname;
    private Toolbar toolbarItems;

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_me, container, false);
        toolbarItems = (Toolbar) view.findViewById(R.id.toolbar_me);
        activity=getActivity();
        file = activity.getSharedPreferences("Vela", 2);
        edit = file.edit();
        cd = new ConnectionDetector(activity);


//        logout_imagebtn=(ImageButton)view.findViewById(R.id.logout_imagebtn);
//
//        logout_imagebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(getActivity(),
//                        SettingActivity.class));
//                getActivity().finish();
//
//
//
//            }
//        });
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) view.findViewById(R.id.txtRatingValue);
        firstname= (TextView) view.findViewById(R.id.firstname);

        firstname.setText( file.getString("name",""));
        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                txtRatingValue.setText(String.valueOf(rating));

            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarItems);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        this.setHasOptionsMenu(true);

        if (!IsViewInited) {
            IsViewInited = true;
            initView();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_me, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        int id = item.getItemId();
        if (id == R.id.action_edit) {

            return true;
        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this.getActivity(), SettingActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_block_user) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogoutConfirmAlertDialouge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Alert!");
        builder.setMessage("Are you sure, you want to Logout?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                        edit.putString("password","").commit();
                        startActivity(new Intent(getActivity(),
                                AuthenticationActivity.class));

                       getActivity().finish();


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

    private void initView() {
     //   replaceFragment(new LoginFragment(), false);
    }

}
