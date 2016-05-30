package com.example.omii026.custompagerlikeanimation;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    /*
    ?Viewpager like custom animation
    */

    CustomPagerBase pager,pager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout cardsLayout = (RelativeLayout)findViewById(R.id.cardsLayout);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        CustomAdapter adapter = new CustomAdapter(this);
        pager = new CustomPagerBase(this, cardsLayout, adapter);
        pager.preparePager(2);

    }
    public class CustomAdapter extends CustomPagerAdapter{

        public CustomAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView) {
            View tempView = convertView;
            if(tempView == null){
                LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                tempView = inflater.inflate(R.layout.single_card_fragment, null, false);
            }
            ImageView cardImage = (ImageView)tempView.findViewById(R.id.cardImage);
            cardImage.setImageDrawable(MainActivity.this.getResources().getDrawable(R.drawable.burger1));
            return tempView;
        }

        @Override
        public int dataCount() {
            return 5;
        }

    }
}
