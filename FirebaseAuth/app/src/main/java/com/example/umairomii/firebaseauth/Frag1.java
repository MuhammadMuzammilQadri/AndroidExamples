package com.example.umairomii.firebaseauth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.umairomii.firebaseauth.CloudMessaging.CloudMesgActivity;
import com.example.umairomii.firebaseauth.Storage.StorageActivity;
import com.google.firebase.crash.FirebaseCrash;

/**
 * Created by umairomii on 5/26/16.
 */
public class Frag1 extends Fragment {

    private FragmentInteraction mListener;

    public Frag1(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.login_frag,container,false);
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));

        Button auth = (Button) view.findViewById(R.id.auth);
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mListener.Auth();
            }
        });

        Button cloudMsg = (Button) view.findViewById(R.id.cloudMsg);
        cloudMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),CloudMesgActivity.class);
                startActivity(i);
            }
        });

        Button stroage = (Button) view.findViewById(R.id.Storage);
        stroage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),StorageActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
    interface FragmentInteraction{
        void Auth();
        void CloudMsg();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (FragmentInteraction) getActivity();
    }
}
