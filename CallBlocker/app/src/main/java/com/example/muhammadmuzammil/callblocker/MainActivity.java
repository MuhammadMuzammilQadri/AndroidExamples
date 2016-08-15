package com.example.muhammadmuzammil.callblocker;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText blockNumberET;
    Button setButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blockNumberET = (EditText) findViewById(R.id.blockNumberET);
        setButton = (Button) findViewById(R.id.setButton);
        setButton.setEnabled(blockNumberET.getText().toString().trim().length() > 0);

        blockNumberET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0)
                    setButton.setEnabled(true);
                else
                    setButton.setEnabled(false);
            }
        });

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = blockNumberET.getText().toString();
//                if (number.trim().isEmpty())
//                    return;
                BlockNumbersHandler.getInstance().setBlockedNumber(number);
                Toast.makeText(MainActivity.this, "Number set", Toast.LENGTH_SHORT).show();
                blockNumberET.setText("");

            }
        });
    }
}
