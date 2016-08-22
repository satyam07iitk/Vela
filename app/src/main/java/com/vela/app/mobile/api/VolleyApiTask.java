package com.vela.app.mobile.api;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.internal.Excluder;
import com.vela.app.mobile.app.Logger;
import com.vela.app.mobile.app.CSError;
import com.vela.app.mobile.models.User;
import com.vela.app.mobile.models.response.Response;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class VolleyApiTask<T extends Response> extends ApiTask<T> implements Listener<JSONObject>, ErrorListener {
	
	public VolleyApiTask(Context context, String url, APIMethod method, Class<T> responseObjectClass, IApiTaskResponseHandler<T> apiTaskResponseHandler) {
		super(context, url, method, responseObjectClass, apiTaskResponseHandler);
	}
	
	@Override
	public void executeRequest(Object requestObject) {
		JSONObject jsonObject = null;
		Logger.log("Request Url: " + url);
		if (requestObject != null) {
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
			try {
				String jsonString = gson.toJson(requestObject);
				Logger.log("Request Object: "+ jsonString);
				jsonObject = new JSONObject(jsonString);
			} catch(Exception ex) {
				if (Logger.DEBUG) {
					ex.printStackTrace();
				}
			}
		}

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(this.getMethodType(),
				url, jsonObject, this, this) {
			// adding Authorization header
			public java.util.Map<String,String> getHeaders() throws com.android.volley.AuthFailureError {
				Map<String, String> headers = new HashMap<>();
				headers.put("Authorization", User.getCurrentUser().getAccessToken());
				return headers;
			}
		};

		jsonObjectRequest.setTag(getTag());
		VolleyRequestSingleton.getInstance(this.context).addToRequestQueue(jsonObjectRequest);
	}
	
	@Override
	public void uploadFileRequest(File[] files) {
		throw new IllegalArgumentException("Please dont use this method to upload images.");
	}
	
	public void cancel() {
		VolleyRequestSingleton.getInstance(context).cancelRequest(getTag());
	}
	
	private int getMethodType() {
		switch (this.method) {
			case POST:
				return Method.POST;
			case GET:
				return Method.GET;
			case PUT:
				return Method.PUT;
			case DELETE:
				return Method.DELETE;
			default:
				return Method.GET;
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Logger.log("api error: " + error.getMessage());
		if (this.apiTaskResponseHandler != null) {
			CSError csError = null;
			try {
				byte[] data = error.networkResponse.data;
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
				String jsonObject = new String(data);
				csError = gson.fromJson(jsonObject, CSError.class);
			} catch (Exception e) {

			}
			if (csError == null) {
				csError = getError(error);
			}
			this.apiTaskResponseHandler.onApiFailure(csError);
		}
	}

	@Override
	public void onResponse(JSONObject cachedJsonResponse) {
		try {
			Logger.log("response: "+ cachedJsonResponse.toString());
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			T response = gson.fromJson(cachedJsonResponse.toString(), this.responseObjectClass);
			// TODO: add code for error here
			if (this.apiTaskResponseHandler != null) {
				this.apiTaskResponseHandler.onApiSuccess(response);
			}
		} catch(Exception e) {
			// remove this key or url from cache if there is any api error
			VolleyRequestSingleton.getInstance(context).getRequestQueue().getCache().remove(url);
			if (this.apiTaskResponseHandler != null) {
				this.apiTaskResponseHandler.onApiFailure(getError(0, null));
			}
		}
	}
	
	private CSError getError(int errorCode, String errorMessage) {
		CSError error = null;
		if (errorCode == 401) {
			error = new CSError(CSError.MGSErrorType.UNAUTHORIZED_ERROR, errorMessage);
		} else if (errorCode == 400) {
			error = new CSError(CSError.MGSErrorType.INVALID_CREDENTIALS_ERROR, errorMessage);
		} else if (errorCode == 1001) {
			error = new CSError(CSError.MGSErrorType.USER_ACCOUNT_BLOCKED, errorMessage);
		} else if (errorCode == 409) {
			error = new CSError(CSError.MGSErrorType.CONFLICT_ERROR, errorMessage);
		} else {
			error = new CSError(CSError.MGSErrorType.API_ERROR, errorMessage);
		}
		return error;
	}
	
	private CSError getError(VolleyError volleyError) {
		Logger.log("volley error: " + volleyError);
		return VolleyErrorHelper.getMessage(volleyError, this.context);
	}
	
}
