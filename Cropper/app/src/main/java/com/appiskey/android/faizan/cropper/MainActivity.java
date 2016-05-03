package com.appiskey.android.faizan.cropper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static Bitmap bitmap1;
    public static Bitmap bitmap2;
    DoubleCropper doubleCropper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doubleCropper = (DoubleCropper) findViewById(R.id.doubleCropper);
    }

    public void testClick(View view) {
        bitmap1 = doubleCropper.getCroppedBitmap1();
        bitmap2 = doubleCropper.getCroppedBitmap2();
        startActivity(new Intent(this, Main2Activity.class));
    }
}
