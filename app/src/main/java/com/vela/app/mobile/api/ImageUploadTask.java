package com.vela.app.mobile.api;

import android.content.Context;
import android.os.AsyncTask;

import com.vela.app.mobile.app.Logger;
import com.vela.app.mobile.app.CSError;
import com.vela.app.mobile.models.User;
import com.vela.app.mobile.models.response.Response;
import com.google.gson.Gson;

import java.io.File;
import java.net.UnknownHostException;

/**
 * Use this class foe file upload operation only.
 */
public class ImageUploadTask<T extends Response> extends ApiTask<T> {

	private ImageUploadAsyncTask imageUploadAsyncTask;

	protected ImageUploadTask(Context context, String url, APIMethod method,
							  Class<T> responseObjectClass, IApiTaskResponseHandler<T> apiTaskResponseHandler) {
		super(context, url, method, responseObjectClass, apiTaskResponseHandler);
	}

	@Override
	public void executeRequest(Object requestObject) {
		throw new IllegalArgumentException("Please dont use this method to send/receive json response.");
	}

	@Override
	public void uploadFileRequest(File[] files) {
		imageUploadAsyncTask = new ImageUploadAsyncTask();
		imageUploadAsyncTask.execute(files);
	}

	@Override
	public void cancel() {
		this.apiTaskResponseHandler = null;
		if (imageUploadAsyncTask != null) {
			imageUploadAsyncTask.cancel(true);
			imageUploadAsyncTask = null;
		}
	}

	private class ImageUploadAsyncTask extends AsyncTask<File, Void, T> {

		private CSError error;

		@Override
		protected T doInBackground(File... params) {
			T result = null;
			try {
				Logger.log("upload image");
				MultipartUtility multipartUtility = new MultipartUtility(url, "UTF-8", User.getCurrentUser().getAccessToken());
				for (int i = 0; i < params.length; i++) {
					multipartUtility.addFilePart("uploadImage" + i, params[i]);
				}
				//multipartUtility.addHeaderField("Authorization", User.getCurrentUser().getAccessToken());
				String response = multipartUtility.finish();
				Logger.log("image upload response: " + response);
				result = new Gson().fromJson(response, responseObjectClass);
			} catch (UnknownHostException e) {
				error = new CSError(CSError.MGSErrorType.NO_NETWORK_CONNECTION);
				if (Logger.DEBUG) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				error = new CSError(CSError.MGSErrorType.API_ERROR);
				if (Logger.DEBUG) {
					e.printStackTrace();
				}
			}
			return result;
		}

		@Override
		protected void onPostExecute(T result) {
			super.onPostExecute(result);
			if (apiTaskResponseHandler != null) {
				if (error != null) {
					apiTaskResponseHandler.onApiFailure(error);
				} else {
					if (result.getErrorCode() > 0) {
						CSError error = new CSError(CSError.MGSErrorType.API_ERROR, result.getErrorMessage());
						apiTaskResponseHandler.onApiFailure(error);
					} else {
						apiTaskResponseHandler.onApiSuccess(result);
					}
				}
			}
		}
	}
}
