package com.vela.app.mobile.ui.eye;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vela.app.mobile.R;
import com.vela.app.mobile.ui.base.BaseContainerFragment;

public class EyeFragment extends BaseContainerFragment {

    private boolean IsViewInited;
    public static EyeFragment newInstance() {
        EyeFragment fragment = new EyeFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_eye, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!IsViewInited) {
            IsViewInited = true;
            initView();
        }
    }

    private void initView() {
     //   replaceFragment(new LoginFragment(), false);
    }

}
