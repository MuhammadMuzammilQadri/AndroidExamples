package com.example.muhammadmuzammil.callblocker;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
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
    Button removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blockNumberET = (EditText) findViewById(R.id.blockNumberET);
        setButton = (Button) findViewById(R.id.setButton);
        removeButton = (Button) findViewById(R.id.removeButton);

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
                forwardCall();

            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeForwardCall();
            }
        });
    }


    private void forwardCall() {

        String forwadingCode = "**67*";
        String forwardingNumber = "4073216182#";

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(String.format("tel:%s", Uri.encode(forwadingCode+forwardingNumber))));
//        intent.setData(Uri.parse("tel:"+forwadingCode+forwardingNumber));
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void removeForwardCall() {

        String forwadingCode = "##67#";

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(String.format("tel:%s", Uri.encode(forwadingCode))));
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
