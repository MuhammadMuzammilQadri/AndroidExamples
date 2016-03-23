package com.example.adnan.panavolley;

import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String url = "http://jsonplaceholder.typicode.com/posts";
    String currencyUrl = "https://openexchangerates.org/api/latest.json?app_id=39c04d08a46e4f6a80bcce0b38c89ec6";
    String data;
    String google = "https://www.google.com/?gws_rd=ssl";
    Button stringReq;
    String netWorkLoaderImage = "http://www.androidcentral.com/sites/androidcentral.com/files/postimages/684/podcast_featured_3.jpg";
    RequestQueue singleTonRequestQueue;
    Button btn;
    TextView currency;
    ImageView networkImageLoader;
    Button array;

    Button imageLoader;
    ImageLoader Imgload;
    String strUrl = "http://httpbin.org/html";
    String imageUrl1 = "http://www.aaj.tv/wp-content/uploads/2015/05/ax.jpg";
    String imageUrl2 = "http://www.aaj.tv/wp-content/uploads/2015/05/ax.jpg";
    String jsonArray = "http://jsonplaceholder.typicode.com/posts";
    String jsonObject = "https://openexchangerates.org/api/latest.json?app_id=39c04d08a46e4f6a80bcce0b38c89ec6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        casts();
        click();
        singleTonRequestQueue = volleySingleton.getInstance().getRequestQueue();

    }

    public void casts() {
        btn = (Button) findViewById(R.id.jsonArray);
        networkImageLoader = (ImageView) findViewById(R.id.imageView);
        imageLoader = (Button) findViewById(R.id.imageLoader);
        stringReq = (Button) findViewById(R.id.StringReq);
        array = (Button) findViewById(R.id.jsonObject);
        Imgload = volleySingleton.getInstance().getImgLoad();
    }

    public void click() {

        imageLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleNetworkImageLoading(imageUrl1);
            }
        });
        stringReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringRequestParse(strUrl);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonArrayRequest(jsonArray);
            }
        });
        array.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonObjectRequest(jsonObject);
            }
        });
    }



    public void simpleNetworkImageLoading(String Url) {
        Imgload.get(Url, new ImageLoader.ImageListener() {      //ImageLoader  handles requestQueue behind the scenes..
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                networkImageLoader.setImageBitmap(imageContainer.getBitmap());

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });

    }

    public void stringRequestParse(String urlObject) {
        StringRequest req = new StringRequest(Request.Method.GET, urlObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Toast.makeText(MainActivity.this, s.toString().substring(100, 300), Toast.LENGTH_SHORT).show();
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
        singleTonRequestQueue.add(req);
    }

    public void jsonArrayRequest(String url) {
        JsonArrayRequest arrayReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("muz", "" + jsonArray.length());
//                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);      //Just toasting the very first index
                        String title = jsonObject.getString("title");
                        String ID = jsonObject.getString("id");
                        String body = jsonObject.getString("body");
                        data = "Title : " + title + "\n" + "Body : " + body + "\n" + "Id : " + ID + "\n";
                        Toast.makeText(MainActivity.this, "This Is the Result ::" + data, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {

                    }
                    Toast.makeText(MainActivity.this, jsonArray.toString().substring(0, 300), Toast.LENGTH_SHORT).show();

//                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        singleTonRequestQueue.add(arrayReq);
    }

    public void jsonObjectRequest(String objectUrl) {
        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.GET, objectUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString().substring(300), Toast.LENGTH_SHORT).show();

            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        singleTonRequestQueue.add(objRequest);


    }



}


