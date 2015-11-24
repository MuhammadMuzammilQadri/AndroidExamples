package muzammil.muhammad.viewpagerpractice;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager mPager = (ViewPager) findViewById(R.id.viewPager);

        mPager.setPageTransformer(true, new YahooLikePageTransformer());
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        AddTabsInTabLayout(tabLayout);
//        tabLayout.setupWithViewPager(mPager); //Cant use it, because we have to manipulate tabs differently..
        SetupTabStrip(tabLayout);
        ImplementSetOnTabSelectedListener(mPager, tabLayout);
        ImplementAddOnPageChangeListener(mPager, tabLayout);
    }

    private static void ImplementAddOnPageChangeListener(ViewPager mPager, final TabLayout tabLayout) {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private static void ImplementSetOnTabSelectedListener(final ViewPager mPager, TabLayout tabLayout) {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("MuzammilQadri", "in onTabSelected " + tab.getPosition());
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("MuzammilQadri","in onTabReselected " + tab.getPosition());
                mPager.setCurrentItem(tab.getPosition());
            }
        });
    }


    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return FirstFragment.newInstance("Muhammad");
                case 1:
                    return SecondFragment.newInstance("Muzammil");
                case 2:
                    return ThirdFragment.newInstance("Qadri");
                default:
                    return DemoFragment.newInstance("Fragment "+(pos+1));
            }
        }

        @Override
                public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ""+position;
        }
    }

    private static void AddTabsInTabLayout(TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"),0,true);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"),1);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"),2);
        tabLayout.addTab(tabLayout.newTab().setText("     "),3);

    }

    private static void SetupTabStrip(TabLayout tabLayout) {
        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        tabStrip.setBackgroundColor(0x000000);
        tabStrip.getChildAt(3).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i("MuzammilQadri", "Tab Touched..");
                return true;
            }
        });
    }

}

