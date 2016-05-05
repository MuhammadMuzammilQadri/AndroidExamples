package com.rubbersoft.android.dynamicview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    FlowLayout flowLayout;
    LayoutInflater inflater;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        layout1();
        layout2();
    }

    void layout1(){
        Button button = (Button) findViewById(R.id.add);
        linearLayout = (LinearLayout) findViewById(R.id.main);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTv();
            }
        });
        addTv();

    }

    void layout2(){
        Button button = (Button) findViewById(R.id.add);
        flowLayout = (FlowLayout) findViewById(R.id.main);
        editText = (EditText) findViewById(R.id.et);
        assert button != null;
/*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTv2();
            }
        });
        addTv2();
*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTv3();
            }
        });
        addTv3();
    }

    void addTv(){
        TextView textView = new TextView(this);
        textView.setText("testing");
        linearLayout.addView(textView);
    }
    void addTv2(){
        TextView textView = new TextView(this);
        textView.setText("testing");
//        textView.setTextColor(0xfff);
//        textView.setBackgroundColor(0x000);
        flowLayout.addView(textView);
    }
    void addTv3(){
//        View view = View.inflate(this,R.layout.ad,flowLayout);
        View view = inflater.inflate(R.layout.ad,null,false);
        TextView textView = (TextView) view;
        textView.setText(editText.getText());
        flowLayout.addView(view,-1,new FlowLayout.LayoutParams(10,10));
    }
}
