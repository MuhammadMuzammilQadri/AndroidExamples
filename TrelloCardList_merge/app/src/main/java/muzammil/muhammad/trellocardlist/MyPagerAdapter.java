package muzammil.muhammad.trellocardlist;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by panacloud on 8/11/15.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
//            case 0: // Fragment # 0 - This will show FirstFragment
//                return FirstFragment.newInstance(0, "Page # 1");
//            case 1: // Fragment # 0 - This will show FirstFragment different title
//                return FirstFragment.newInstance(1, "Page # 2");

            case 0: // Fragment # 0 - This will show FirstFragment
                return new Fragment1();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new Fragment2();
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return new Fragment3();

            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}
