package com.vela.app.mobile.api;


import com.vela.app.mobile.app.CSError;
import com.vela.app.mobile.app.UnauthorisedUtility;

public class ApiResponseHandler<T> implements ApiTask.IApiTaskResponseHandler<T> {
	
	@Override
	public void onApiSuccess(T response) {
		
	}

	@Override
	public void onApiFailure(CSError error) {
		if (error.getErrorType() == CSError.MGSErrorType.USER_ACCOUNT_BLOCKED) {
			UnauthorisedUtility.processUserAccountBlocked();
		}
		if (error.getErrorType() == CSError.MGSErrorType.UNAUTHORIZED_ERROR) {
			UnauthorisedUtility.processUnauthentication();
		}
	}

}
