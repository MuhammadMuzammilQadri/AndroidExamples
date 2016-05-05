package com.az3ez.samplemeezan;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

// Implementation References:
// https://developer.android.com/training/volley/requestqueue.html
// http://arnab.ch/blog/2013/08/asynchronous-http-requests-in-android-using-volley/
public class HttpRequestHandler {


    private static final String TAG = "HttpRequestHandler";
    private static HttpRequestHandler httpRequestHandler;
    private RequestQueue mRequestQueue;
    private static Context mContext;
    private ImageLoader imageLoader;

    private HttpRequestHandler(){
    }

    public static void setAndroidContext(Context context){
        mContext = context;
    }

    public static HttpRequestHandler getInstance()throws RuntimeException {
        if(mContext==null){
            throw new RuntimeException("Context is not set, call HttpRequestHandler.setAndroidContext(context) first");
        }
        if(httpRequestHandler==null){
            httpRequestHandler = new HttpRequestHandler();
        }
        return httpRequestHandler;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    // Sample Object for Response.Listener
    /*
    new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d(TAG, response.toString());
        }
    }
    */

    // Sample Object for Response.ErrorListener
    /*
    new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "Error: " + error.getMessage());
        }
    }
    */
    // Important: You don't have to use Async Task to call these methods
    public void get(String url,Response.Listener responseListener, Response.ErrorListener responseErrorListener){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,url, responseListener,responseErrorListener);
        // Adding request to request queue
        this.addToRequestQueue(jsonObjReq, TAG);
    }

    public <T> void post(String url, T obj, Response.Listener responseListener, Response.ErrorListener responseErrorListener)throws JSONException {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,url, GsonUtils.toJSON(obj),responseListener,responseErrorListener);
            // Adding request to request queue
            this.addToRequestQueue(jsonObjReq, TAG);
    }

    public void post(String url, org.json.JSONObject obj, Response.Listener responseListener, Response.ErrorListener responseErrorListener)throws JSONException {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,url,obj,responseListener,responseErrorListener);
        // Adding request to request queue
        this.addToRequestQueue(jsonObjReq, TAG);
    }

    public void postStringRequest(String url, org.json.JSONObject obj, Response.Listener responseListener, Response.ErrorListener responseErrorListener)throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  responseListener, responseErrorListener ) {
            // this is the relevant method
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("CustomerName", "Usman");
                // volley will escape this for you
                params.put("Customerid", "130734");
                return params;
            }
        };
        // Adding request to request queue
        this.addToRequestQueue(stringRequest, TAG);
    }


    public void postStringRequestWithBody(String url, final org.json.JSONObject obj, Response.Listener responseListener, Response.ErrorListener responseErrorListener)throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  responseListener, responseErrorListener ) {
            // this is the relevant method

            /*@Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("body","application/x-www-form-urlencoded");
                return params;
            }*/

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return obj.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        // Adding request to request queue
        this.addToRequestQueue(stringRequest, TAG);
    }
}
