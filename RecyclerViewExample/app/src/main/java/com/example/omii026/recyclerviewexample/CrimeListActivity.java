package com.example.omii026.recyclerviewexample;

import android.support.v4.app.Fragment;

/**
 * Created by Omii026 on 1/5/2016.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
