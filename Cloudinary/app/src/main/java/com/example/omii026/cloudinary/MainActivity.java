package com.example.omii026.cloudinary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String url;
    private Bitmap bitmap;
    private ImageView imageView;
    private Button upload, download;
    private Cloudinary cloudinary;
    private File selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //helpFull links..

        // https://github.com/koush/UrlImageViewHelper
        // http://cloudinary.com/documentation/java_integration#display_and_manipulate_images
        // https://github.com/cloudinary/cloudinary_java/tree/master/cloudinary-android



        // configuration method # 1

//        Map config = new HashMap();
//        config.put("cloud_name", "testingapp");
//        config.put("api_key", "531315269532892");
//        config.put("api_secret", "T3gsMEfaljLiBrTZljQ-RC8Zwe8");
//        Cloudinary cldinary = new Cloudinary(config);

        // configuration method # 2
        cloudinary = new Cloudinary("cloudinary://531315269532892:T3gsMEfaljLiBrTZljQ-RC8Zwe8@testingapp");


        imageView = (ImageView) findViewById(R.id.image);
        upload = (Button) findViewById(R.id.button1);
        download = (Button) findViewById(R.id.button2);

//         url = cloudinary.url().format("jpg")
//                .transformation(new Transformation().width(250).height(168).crop("fit"))
//                .generate("sample");

//        Log.d("Home", "URL->" + url);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, 0);


            }
        });

        download.setEnabled(false);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(url != null) {
                    Downloader up = new Downloader();
                    up.execute();
                }
            }
        });
    }

    public class Downloader extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {

            //Method # 1
 //            try {
//                URL uurl = new URL(url);
//                URLConnection conn = uurl.openConnection();
//                conn.connect();
//                InputStream is = conn.getInputStream();
//                bitmap = BitmapFactory.decodeStream(is);
//
//          return true;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            //Method# 2
            try {
                 bitmap = BitmapFactory.decodeStream((InputStream) new URL(url.toString()).getContent());
            return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Log.d("Home", "true");

//                imageView.setImageBitmap();
                imageView.setImageBitmap(bitmap);
            }else{
                Log.d("Home","false");

            }

            super.onPostExecute(aBoolean);
        }
    }

    public class Upload extends AsyncTask<Void,Void,Boolean>{

        Context context;
        Upload(Context context){
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            File file = new File("demo.jpg");
            try {
                Map upload = cloudinary.uploader().upload(selectedImage, ObjectUtils.emptyMap());
                url = upload.get("url").toString();
                Log.d("upload",""+upload.get("url"));
                Log.d("upload",""+upload.get("secure_url"));
                Log.d("upload",""+upload.get("format"));
                Log.d("upload",""+upload.get("resource_type"));
                Log.d("upload",""+upload.get("created_at"));
                Log.d("upload",""+upload.get("type"));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean){
                Log.d("Home/upload","true");
                Toast.makeText(context,"image is upload :)",Toast.LENGTH_SHORT).show();
                download.setEnabled(true);
            }else{
                Log.d("Home/upload","false");

            }

            super.onPostExecute(aBoolean);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && data != null){
            selectedImage = new File(data.getData().getPath());

            Toast.makeText(getApplicationContext(),"Wait 4 a second",Toast.LENGTH_SHORT).show();
            Upload uploader = new Upload(getApplicationContext());
            uploader.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
