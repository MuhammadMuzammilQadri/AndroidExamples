package com.example.muhammadmuzammil.fragmentcommunication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Muhammad Muzammil on 7/2/2015.
 */



public class FragmentA extends Fragment {

    TextChangeListener listener;

    public interface TextChangeListener {
        public void onTextChange(CharSequence newText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.v("Muz", "In FragmentA before inflate");

        View view = inflater.inflate(R.layout.fragment_a, container, false);

        Log.v("Muz", "In FragmentA after inflate");


        Button btn = (Button) view.findViewById(R.id.button1);
        final TextView textView = (TextView) view.findViewById(R.id.textView1);

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onTextChange(textView.getText());
                }

            }
        });
        return view;
    }

    public void setTextChangeListener(TextChangeListener listener) {
        this.listener = listener;
    }

    public void anyfunction(){

    }
}