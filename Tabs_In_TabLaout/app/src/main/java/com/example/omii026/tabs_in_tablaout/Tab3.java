package com.example.omii026.tabs_in_tablaout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Omii026 on 12/23/2015.
 */
public class Tab3 extends Fragment {

     public static ChildAdapter adapter;
    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_tab3,container,false);

        Log.d("TAG","OnCreate TAB3");
        TabLayout tab = (TabLayout) rootView.findViewById(R.id.tablayout2);
        tab.addTab(tab.newTab().setText("Child1"));
        tab.addTab(tab.newTab().setText("Child2"));
        tab.addTab(tab.newTab().setText("Child3"));
        tab.addTab(tab.newTab().setText("Child4"));
        tab.addTab(tab.newTab().setText("Child5"));

         adapter = new ChildAdapter(getActivity().getSupportFragmentManager(),tab.getTabCount());

        final ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager2);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        adapter.notifyDataSetChanged();

        return rootView;
         }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("TAG", "DestroyView Tab3");
    }

}
