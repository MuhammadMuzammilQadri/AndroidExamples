package com.aik.spannableedittextexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et = (EditText) findViewById(R.id.edittext);

        Button button = (Button) findViewById(R.id.pressMeButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int startSelection = et.getSelectionStart();
                int endSelection = et.getSelectionEnd();

                String selectedText = et.getText().toString().substring(startSelection, endSelection);
                String unselectedTextPartOne = "";

                if (startSelection != 0)
                    unselectedTextPartOne = et.getText().toString().substring(0, startSelection);

                String unselectedTextPartTwo = et.getText().toString().substring(endSelection, et.getText().length());


                SpannableString ss = new SpannableString(unselectedTextPartOne + selectedText + unselectedTextPartTwo);


                ClickableSpan customSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {

                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setTextSize(26);
                        ds.setUnderlineText(false);
                        ds.setColor(getColor(R.color.colorText));
                    }
                };

                ss.setSpan(customSpan,
                        startSelection, endSelection, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                et.setText(ss);

            }
        });

    }
}
