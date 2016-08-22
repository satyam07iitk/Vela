package com.vela.app.mobile.app;

import com.vela.app.mobile.R;

public class CSError {
	
	private String message;
	private MGSErrorType errorType;
	
	public CSError() {
		this.errorType = MGSErrorType.ERROR;
		this.message = CSApplication.getInstance().getApplicationContext().getResources().getString(R.string.error);
	}
	
	public CSError(MGSErrorType errorType) {
		this.errorType = errorType;
		this.message = this.getErrorMessage();
	}
	
	public CSError(MGSErrorType errorType, String message) {
		this.errorType = errorType;
		this.message = message;
	}
	
	public String getMesssage() {
		return this.message;
	}
	
	public MGSErrorType getErrorType() {
		return this.errorType;
	}
	
	private String getErrorMessage() {
		String errorMessage = CSApplication.getInstance().getApplicationContext().getResources().getString(R.string.error);
		switch (this.errorType) {
			case NO_NETWORK_CONNECTION:
				errorMessage = CSApplication.getInstance().getApplicationContext().getResources().getString(R.string.no_internet_connection);
				break;
			case LOCATION_UNAVAILABLE_ERROR:
				errorMessage = CSApplication.getInstance().getApplicationContext().getResources().getString(R.string.location_unavailable);
				break;
			case API_ERROR:			
			case ERROR:
			default:
				errorMessage = CSApplication.getInstance().getApplicationContext().getResources().getString(R.string.error);
				break;
		}
		return errorMessage;
	}
		
	@Override
	public boolean equals(Object obj) {
		return this.errorType == ((CSError) obj).errorType;
	}
	
	
	
	public static enum MGSErrorType {
		ERROR,
		NO_NETWORK_CONNECTION,
		UNAUTHORIZED_ERROR,
		INVALID_CREDENTIALS_ERROR,
		CONFLICT_ERROR,
		API_ERROR,
		FB_LOGIN_ERROR,
		TWITTER_LOGIN_ERROR,
		NO_USER_LOGGED_IN,
		USER_ACCOUNT_BLOCKED,
		LOCATION_UNAVAILABLE_ERROR
	}

}
