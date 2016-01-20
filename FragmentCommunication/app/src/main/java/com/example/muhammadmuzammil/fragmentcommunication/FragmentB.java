package com.example.muhammadmuzammil.fragmentcommunication;

import android.support.v4.app.Fragment;

/**
 * Created by Muhammad Muzammil on 7/2/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentB extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        textView = (TextView) view.findViewById(R.id.textView1);
        return view;
    }

    public void updateTextValue(CharSequence newText) {
        textView.setText(newText);
    }
}