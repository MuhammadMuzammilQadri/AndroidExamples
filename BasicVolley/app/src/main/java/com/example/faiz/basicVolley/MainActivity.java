package com.example.faiz.basicVolley;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.faiz.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    RequestQueue queue;
    TextView mTextView;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

//      Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        setPostData();

//        jsonObjectRequest();
//        stringRequest();
        JsonArrayRequest();


    }

    public void setPostData(){
//        JSONObject to send post request arguments
        jsonObject = new JSONObject();

        try {
//            setting data
            jsonObject.put("CustomerName",getString(R.string.CustomerName));
            jsonObject.put("Customerid",getString(R.string.Customerid));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void jsonObjectRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST
                , getString(R.string.request_url2)
                , jsonObject
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mTextView.setText(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(getApplication(),"bad",Toast.LENGTH_SHORT).show();
            }
        }
        );

        queue.add(jsonObjectRequest);

    }

    public void stringRequest(){

        StringRequest sr = new StringRequest(Request.Method.POST, getString(R.string.request_url2), new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
//                    JSONObject jsonObject1 = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(response);
                    mTextView.setText(jsonArray.getJSONObject(0).toString());
//                    mTextView.setText(jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mTextView.setText(response);
                Log.d("test",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Customerid", getString(R.string.Customerid));
                params.put("CustomerName", getString(R.string.CustomerName));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        queue.add(sr);

    }

//    create a json array request
    public void JsonArrayRequest(){
//        create success listener
        Response.Listener<JSONArray> jsonArrayResponseListener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                mTextView.setText(jsonArray.toString());
                Log.d("TEST",jsonArray.toString());
            }
        };

//        create errot listener
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        };

//        create custom json array request
        MYJsonArrayRequest myJsonArrayRequest = new MYJsonArrayRequest(
                getString(R.string.request_url2),
                jsonObject,
                jsonArrayResponseListener ,
                errorListener
        );

//        adding request to queue for execution
        queue.add(myJsonArrayRequest);
    }

}

/*
    custom json request array implementation
    just copy past from decomplie class for JsonArrayRequest
    because default implementation is for GET request only
* */
class MYJsonArrayRequest extends JsonRequest<JSONArray> {
    public MYJsonArrayRequest(String url, JSONObject jsonRequest, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, jsonRequest.toString(), listener, errorListener);
    }

    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String je = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(je), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException var3) {
            return Response.error(new ParseError(var3));
        } catch (JSONException var4) {
            return Response.error(new ParseError(var4));
        }
    }
}
