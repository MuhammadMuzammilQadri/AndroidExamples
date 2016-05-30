package com.example.omii026.custompagerlikeanimation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Omii026 on 5/5/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 1:

        }

        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
