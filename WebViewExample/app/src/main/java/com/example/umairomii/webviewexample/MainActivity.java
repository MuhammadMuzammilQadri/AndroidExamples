package com.example.umairomii.webviewexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.container,new BlankFragment(), "webView").addToBackStack(null).commit();
            }
        });

        ((Button) findViewById(R.id.btn2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK && data != null){
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap((Bitmap) data.getExtras().get("data"));

        }

    }

    @Override
    public void onBackPressed() {
        BlankFragment fragment = (BlankFragment)
                getSupportFragmentManager().findFragmentByTag("webView");
        if(fragment != null) {
            if (fragment.canGoBack()) {
                fragment.goBack();
            }else{
                super.onBackPressed();
            }
        }else {
            super.onBackPressed();
            }


    }
}
