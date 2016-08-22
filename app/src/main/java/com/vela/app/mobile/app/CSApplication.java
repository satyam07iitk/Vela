package com.vela.app.mobile.app;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class CSApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.

	private static ProgressDialog dialog=null;
	public static final String TAG = CSApplication.class.getSimpleName();

	private static CSApplication mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	public static synchronized CSApplication getInstance() {
		return mInstance;
	}

	public static void spinnerStart(Context context) {
		String pleaseWait = "please wait...";
		spinnerStop();
		dialog = ProgressDialog.show(context, "", pleaseWait, true);
	}

	public static void spinnerStop() {

		try {

			if (dialog != null) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void popErrorMsg(String titleMsg, String errorMsg,
								   Context context) {
		// pop error message
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(titleMsg).setMessage(errorMsg)
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void ShowMassage(Context ctx, String msg) {
		Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();


	}
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
	}
}
