package muzammil.muhammad.viewpagerpractice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager mPager = (ViewPager) findViewById(R.id.viewPager);
        mPager.setPageTransformer(true, new YahooLikePageTransformer());
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

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
                    return FirstFragment.newInstance("Default First Fragment");
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}