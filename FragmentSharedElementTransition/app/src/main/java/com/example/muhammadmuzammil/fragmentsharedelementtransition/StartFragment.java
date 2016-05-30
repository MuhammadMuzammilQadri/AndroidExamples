package com.example.muhammadmuzammil.fragmentsharedelementtransition;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Muhammad Muzammil on 16-Jan-16.
 */
public class StartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_start, container, false);
        rootView.findViewById(R.id.fragment_start_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(rootView, StartFragment.this, new EndFragment() );
            }
        });


        return rootView;
    }



}
