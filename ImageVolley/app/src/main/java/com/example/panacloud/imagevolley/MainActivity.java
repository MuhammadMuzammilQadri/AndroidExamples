package com.example.panacloud.imagevolley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends ActionBarActivity {

    private String url = "https://s3-us-west-2.amazonaws.com/defaultimgs/teamofteams.png";
    private ImageView iv;
    private Button button;
    private RequestQueue rq;
    private ImageRequest ir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
    }


    public void Init ()
    {

        iv = (ImageView) findViewById(R.id.myImage);
        button = (Button) findViewById(R.id.button);


       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               rq = Volley.newRequestQueue(MainActivity.this);

               ir = new ImageRequest(url,new Response.Listener<Bitmap>() {
                   @Override
                   public void onResponse(Bitmap bitmap) {


                       iv.setImageBitmap(bitmap);
                       Toast.makeText(MainActivity.this,"request done",Toast.LENGTH_SHORT).show();

                   }
               }, 0,0,null,new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError volleyError) {
                       Toast.makeText(MainActivity.this,"Error cannot load the image",Toast.LENGTH_SHORT).show();
                   }
               });
               rq.add(ir);

           }

       });

    }
}
