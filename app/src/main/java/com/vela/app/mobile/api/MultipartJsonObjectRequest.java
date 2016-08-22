package com.vela.app.mobile.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vela.app.mobile.app.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MultipartJsonObjectRequest extends Request<String> {
	
	
	
	
	
	public MultipartJsonObjectRequest(int method, String url, File file,
                                      ErrorListener listener) {
		super(method, url, listener);
		// TODO Auto-generated constructor stub
		this.file = file;
		
	}

	File file;
	

//	public MultipartJsonObjectRequest(int method, String url,
//			File file, JSONObject jsonRequest, Listener<JSONObject> listener,
//			ErrorListener errorListener) {
//		super(method, url, jsonRequest, listener, errorListener);
//		this.file = file;
//		// TODO Auto-generated constructor stub
//	}

	@Override
	public String getBodyContentType() {
	    return "multipart/form-data";
	}
	
	
	private static final String LINE_FEED = "\r\n";
	
	private final String boundary = "===" + System.currentTimeMillis() + "===";
	
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-Type", "multipart/form-data; boundary=" + boundary);
		return map;
		
	}

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter writer = null;
		try {
			writer = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, "UTF-8"),
			        true);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        String fileName = file.getName() + ".jpeg";
        String boundary = "===" + System.currentTimeMillis() + "===";
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"uploadImage0\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        writer.append(
                "Content-Type: "
                        + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        
        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();
        
        try {
 
        FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byteArrayOutputStream.flush();
        inputStream.close();
        
        } catch(Exception e) {
        	if (Logger.DEBUG) {
        		e.printStackTrace();
        	}
        }
         
        writer.append(LINE_FEED);
        writer.flush(); 
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
    	String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        Logger.log("Network Response from server: " + parsed);
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response)
    {
    	Logger.log("Response from server: " + response);
       // mListener.onResponse(response);
    }
}
