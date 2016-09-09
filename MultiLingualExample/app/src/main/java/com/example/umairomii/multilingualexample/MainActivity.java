package com.example.umairomii.multilingualexample;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration config = getResources().getConfiguration();
                Locale locale = new Locale("it");
                config.locale = locale;
                getResources().updateConfiguration(config,getResources().getDisplayMetrics());
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        });


    }
}
