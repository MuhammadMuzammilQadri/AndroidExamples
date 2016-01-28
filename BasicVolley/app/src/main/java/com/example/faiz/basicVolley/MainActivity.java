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
import com.android.volley.VolleyLog;
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

        stringRequestWithData(getString(R.string.send_data_url));

        stringRequest(getString(R.string.request_url1));
        stringRequest(getString(R.string.request_url2));

        jsonObjectRequest(getString(R.string.request_url1));
        jsonObjectRequest(getString(R.string.request_url2));

        JsonArrayRequest(getString(R.string.request_url1));
        JsonArrayRequest(getString(R.string.request_url2));



    }

    public void setPostData() {
//        JSONObject to send post request arguments
        jsonObject = new JSONObject();

        try {
//            setting data
            jsonObject.put("CustomerName", getString(R.string.CustomerName));
            jsonObject.put("Customerid", getString(R.string.Customerid));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void stringRequest(final String url) {

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                mTextView.setText(response);
                Log.d("response", "url-->" + url + "--value-->" + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("responsearray", "url-->" + url + "--value-->" + jsonArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    Log.d("responseobj", "url-->" + url + "--value-->" + jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    public void stringRequestWithData(final String url) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(getString(R.string.send_data_key), getString(R.string.send_data_string));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyStringRequest myStringRequest = new MyStringRequest(Request.Method.POST, url, jsonObject.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                mTextView.setText(response);
                Log.d("response", "url-->" + url + "--value-->" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(myStringRequest);
    }

    public void jsonObjectRequest(final String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST
                , url
                , jsonObject
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i("response", "url-->" + url + "--value-->" + jsonObject.toString());
                mTextView.setText(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(getApplication(), "bad", Toast.LENGTH_SHORT).show();
            }
        }
        );

        queue.add(jsonObjectRequest);

    }

    //    create a json array request
    public void JsonArrayRequest(String url) {
//        create success listener
        Response.Listener<JSONArray> jsonArrayResponseListener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                mTextView.setText(jsonArray.toString());
                Log.d("TEST", jsonArray.toString());
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
                url,
                jsonObject,
                jsonArrayResponseListener,
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
*/
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


/*
     custom string request with data string
*/
class MyStringRequest extends StringRequest {

    private String mRequestBody;

    public MyStringRequest(int method, String url, String data, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mRequestBody = data;
    }

    @Override
    public String getBodyContentType() {
        return String.format("application/json; charset=%s", new Object[]{"utf-8"});
    }

    public byte[] getBody() {
        try {
            return this.mRequestBody == null?null:this.mRequestBody.getBytes("utf-8");
        } catch (UnsupportedEncodingException var2) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", new Object[]{this.mRequestBody, "utf-8"});
            return null;
        }
    }

}
