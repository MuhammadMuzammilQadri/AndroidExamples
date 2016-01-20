package com.example.omii026.tabs_in_tablaout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Omii026 on 12/23/2015.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    int NoOfTAbs;

    public TabAdapter(FragmentManager fm,int NoOfTabs) {
        super(fm);
    this.NoOfTAbs = NoOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new Tab1();
        }else if(position == 1){

            return new Tab2();
        } else if(position == 2){
            return new Tab3();
        } else if(position == 3){

            return new Tab4();
        }else if(position == 4){
            return new Tab5();
        }else
            return null;
    }

    @Override
    public int getCount() {
        return NoOfTAbs;
    }
}
