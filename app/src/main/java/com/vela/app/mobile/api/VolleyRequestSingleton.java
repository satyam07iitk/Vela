package com.vela.app.mobile.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyRequestSingleton {

	private static VolleyRequestSingleton apiManager;

	private RequestQueue requestQueue;
	private ImageLoader imageLoader;
	private Context context;

	public VolleyRequestSingleton(Context context) {
		this.context = context;
		this.requestQueue = this.getRequestQueue();

		this.imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
			private final LruCache<String, Bitmap>
			cache = new LruCache<String, Bitmap>(100);

			@Override
			public Bitmap getBitmap(String url) {
				return this.cache.get(url);
			}

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				this.cache.put(url, bitmap);
			}
		});
	}

	public static synchronized VolleyRequestSingleton getInstance(Context context) {
		if (VolleyRequestSingleton.apiManager == null) {
			VolleyRequestSingleton.apiManager = new VolleyRequestSingleton(context);
		}
		return VolleyRequestSingleton.apiManager;
	}

	public RequestQueue getRequestQueue() {
		if (this.requestQueue == null) {
			// getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
			this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
		}
		return this.requestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req) {
		this.getRequestQueue().add(req);
	}

	public ImageLoader getImageLoader() {
		return this.imageLoader;
	}
	
	public void cancelRequest(String tag) {
		this.requestQueue.cancelAll(tag);
	}

}
