package com.vela.app.mobile.api;

import android.content.Context;

import com.vela.app.mobile.models.response.Response;


public class ApiTaskFactory<T extends Response> {
	
	public ApiTask<T> newInstance(Context context, String url, ApiTask.APIMethod method, Class<T> responseObjectClass,
								  ApiTask.IApiTaskResponseHandler<T> apiTaskResponseHandler) {
		return new VolleyApiTask<T>(context, url, method, responseObjectClass, apiTaskResponseHandler);
	}
	
	public ApiTask<T> newImageUploadInstance(Context context, String url, ApiTask.APIMethod method,
											 Class<T> responseObjectClass, ApiTask.IApiTaskResponseHandler<T> apiTaskResponseHandler) {
		return new ImageUploadTask<T>(context, url, method, responseObjectClass, apiTaskResponseHandler);
	}

}
