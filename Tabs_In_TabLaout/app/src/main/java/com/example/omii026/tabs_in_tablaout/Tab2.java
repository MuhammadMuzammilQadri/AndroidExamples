package com.example.omii026.tabs_in_tablaout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Omii026 on 12/23/2015.
 */
public class Tab2 extends Fragment {

    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_tab2,container,false);

        Log.d("TAG", "OnCreate TAB2");
        return rootView;
    }
}
