package com.muzammil.muhammad.callforward;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String forwadingCode = "*72";
        String forwardingNumber = "4073216182";

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+forwadingCode+forwardingNumber));
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
