package com.example.umairomii.webviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }

    @Override
    public void onBackPressed() {
        BlankFragment fragment = (BlankFragment)
                getSupportFragmentManager().findFragmentByTag("webView");
        if (fragment.canGoBack()) {
            fragment.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
