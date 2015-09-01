package muzammil.muhammad.tabspractice;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.text_view);


        setFragment(0); //By default
//        pager = (ViewPager) view.findViewById(R.id.pager);
//        final int[] defaultTabLayouts=new int[3];
//        defaultTabLayouts[0]=R.layout.parallelogram;
//        defaultTabLayouts[1]=R.layout.chat_tab_chat_layout;
//        defaultTabLayouts[2]=R.layout.chat_tab_projects_layout;

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.parallelogram).setText("First"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.parallelogram_second).setText("Second"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.parallelogram_third).setText("Third"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final int tabChildrenCount = tabLayout.getTabCount();
        Log.d("MuzammilQadri", "Count: "+tabChildrenCount);

//        View currentView;
//        for (int i = 0; i < tabChildrenCount; i++) {
//                Log.d("MuzammilQadri", ""+i);
//                currentView = tabLayout.getTabAt(i).
//                FrameLayout.LayoutParams currentLayout =
//                        (FrameLayout.LayoutParams) currentView.getLayoutParams();
//                currentLayout.setMargins(-30, 0, -30, 0);
//
//        }

//        currentView = tabLayout.getChildAt(1);
//        FrameLayout.LayoutParams currentLayout =
//                (FrameLayout.LayoutParams) currentView.getLayoutParams();
//        currentLayout.setMargins(-currentView.getWidth(), 0, -currentView.getWidth(), 0);
//
//        tabLayout.requestLayout();

//        pager.setAdapter(new ChatPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount()));
//        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                todo: assign chat_tab_activity_layout main layout an id and then find it by findview by id and set background..
//                View tempView=defaultTabLayouts[tab.getPosition()];
                setFragment(tab.getPosition());
//                View v = View.inflate(getActivity(), R.layout.test, null);
//                tab.setCustomView(v);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                View v = View.inflate(getActivity(), defaultTabLayouts[tab.getPosition()], null);
//                tab.setCustomView(v);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setFragment(int position) {

        switch (position) {
            case 0:
               textView.setText("First Text"); break;
            case 1:
                textView.setText("Second Text"); break;
            case 2:
                textView.setText("Third Text"); break;
            default:
                return;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
