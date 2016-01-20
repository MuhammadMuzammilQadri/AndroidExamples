package com.example.omii026.tabs_in_tablaout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Omii026 on 12/23/2015.
 */
public class ChildAdapter extends FragmentStatePagerAdapter {

    int NoOfTAbs;

    public ChildAdapter(FragmentManager fm, int NoOfTabs) {
        super(fm);
    this.NoOfTAbs = NoOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new Child1();
            case 1: return new Child2();
            case 2:

                return new Child3();
            case 3: return new Child4();
            case 4: return new Child5();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return NoOfTAbs;
    }
}
