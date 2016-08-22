package com.vela.app.mobile.api;

import android.content.Context;

import com.vela.app.mobile.app.CSError;

import java.io.File;

public abstract class ApiTask<T> {
	
	protected Context context;
	protected String url;
	protected APIMethod method;
	protected Class<T> responseObjectClass;
	protected IApiTaskResponseHandler<T> apiTaskResponseHandler;
		
	protected ApiTask(Context context, String url, APIMethod method, Class<T> responseObjectClass, IApiTaskResponseHandler<T> apiTaskResponseHandler) {
		super();
		this.context = context;
		this.url = url;
		this.method = method;
		this.responseObjectClass = responseObjectClass;
		this.apiTaskResponseHandler = apiTaskResponseHandler;
	}

	protected String getTag() {
		return url;
	}
	
	public abstract void executeRequest(Object requestObject);
	
	public abstract void uploadFileRequest(File[] files);
	
	public abstract void cancel();
	
//	public static ApiTask<T> newInstance(Context context, String url, APIMethod method, Class<T> responseObjectClass, IApiTaskResponseHandler<T> apiTaskResponseHandler) {
//		return new VolleyApiTask(context, url, method, responseObjectClass, apiTaskResponseHandler);
//	}
//	
//	public static ApiTask newImageUploadInstance(Context context, String url, APIMethod method, Class<T> responseObjectClass, IApiTaskResponseHandler<T> apiTaskResponseHandler) {
//		return new ImageUploadTask(context, url, method, responseObjectClass, apiTaskResponseHandler);
//	}
	
	public interface IApiTaskResponseHandler<T> {
		void onApiSuccess(T response);
		void onApiFailure(CSError error);
	}
	
	public enum APIMethod {
		POST, GET, PUT, DELETE
	}

}
