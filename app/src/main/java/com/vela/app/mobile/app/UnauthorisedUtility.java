package com.vela.app.mobile.app;

import android.content.Context;
import android.content.Intent;

import com.vela.app.mobile.models.User;
import com.vela.app.mobile.ui.auth.AuthenticationActivity;

public class UnauthorisedUtility {

	public static void processUnauthentication() {
		Context context = CSApplication.getInstance().getApplicationContext();
		User currentUser = User.getCurrentUser();
		terminateFacebookSession(currentUser);

		// delete all database data
//		DatabaseProvider databaseProvider = DatabaseProvider.getInstance();
//		databaseProvider.deleteAll();
		
		Intent intent = new Intent(context, AuthenticationActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		// start broadcasting for closing all activity in case of logout
		Intent closeActivity = new Intent(Constants.CLOSE_ALL_ACTIVITY_LOGOUT_ACTION);
		context.sendBroadcast(closeActivity);
	}
	
	public static void processUserAccountBlocked() {
//		Context context = CSApplication.getInstance().getApplicationContext();
//		User currentUser = User.getCurrentUser();
////		currentUser.setAccountStatus(User.ACCOUNT_STATUS_BLOCKED);
////		currentUser.updateCurrentUser();
//
//		Intent intent = new Intent(context, AccountBlockedActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		context.startActivity(intent);
//
//		// start broadcasting for closing all activity in case of account blocked
//		Intent closeActivity = new Intent(Constants.CLOSE_ALL_ACTIVITY_ACCOUNT_BLOCKED_ACTION);
//		context.sendBroadcast(closeActivity);
	}

	public static void terminateFacebookSession(User user) {
//		// logged out from fb session
//		Session session = Session.getActiveSession();
//		if (session == null) {
//			session = Session.openActiveSessionFromCache(MGSApplication.getInstance().getApplicationContext());
//		}
//		if (session != null) {
//			session.closeAndClearTokenInformation();
//		}
//		Session.setActiveSession(null);
	}
}
