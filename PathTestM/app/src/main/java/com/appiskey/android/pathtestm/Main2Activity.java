package com.appiskey.android.pathtestm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;

public class Main2Activity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView = (ImageView) findViewById(R.id.image);


        String[] strings = getFilesDir().list();
        for (String s: strings){
            Log.i("testing",s);
        }

        File f = new File(getFilesDir(), "testing");
        String s = f.getPath();
        Log.i("testing",s);

//        filePath = get.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(s);
        imageView.setImageBitmap(bitmap);
/*
        try {
            openFileInput("test");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
*/

    }
}
