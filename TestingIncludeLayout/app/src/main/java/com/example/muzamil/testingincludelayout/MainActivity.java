package com.example.muzamil.testingincludelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.tv);
        TextView newview = (TextView) findViewById(R.id.newview);

        tv.setText("I am tv");
        newview.setText("I am newview");
    }
}
