package com.vela.app.mobile.app;

import android.support.design.BuildConfig;
import android.util.Log;

public class Logger {

	public static final boolean DEBUG = BuildConfig.DEBUG;

    public static void log(int priority, String tag, String msg) {
        if (Logger.DEBUG) {
            Log.println(priority, tag, msg);
        }
    }

	public static void log(String tag, String msg) {
		if (Logger.DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void log(String msg) {
		if (Logger.DEBUG) {
			Log.d("DEBUG", msg);
		}
	}
}
