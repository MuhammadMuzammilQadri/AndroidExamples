package com.shiraz.panacloud.trelloactionbutton;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    ImageButton btn1,btn2,btn3,btn4,feb;
    TextView tv1,tv2,tv3,tv4;
    Animation menuAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (ImageButton) findViewById(R.id.item1);
        btn2 = (ImageButton) findViewById(R.id.item2);
        btn3 = (ImageButton) findViewById(R.id.item3);
        btn4 = (ImageButton) findViewById(R.id.item4);
        feb = (ImageButton) findViewById(R.id.btnFab);

        tv1 = (TextView) findViewById(R.id.text1);
        tv2 = (TextView) findViewById(R.id.text2);
        tv3 = (TextView) findViewById(R.id.text3);
        tv4 = (TextView) findViewById(R.id.text4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "btn1 click", Toast.LENGTH_SHORT).show();
                animations();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "btn2 click", Toast.LENGTH_SHORT).show();
                animations();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "btn3 click", Toast.LENGTH_SHORT).show();
                animations();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "btn4 click", Toast.LENGTH_SHORT).show();
                animations();
            }
        });
        feb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "feb click", Toast.LENGTH_SHORT).show();
                animations();
            }
        });
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

    public void animations() {
        if (btn1.getVisibility() == View.VISIBLE) {
            AnimationSet animationSet = new AnimationSet(false);
            Animation slide_out = AnimationUtils.loadAnimation(this, R.anim.abc_slide_out_bottom);
            menuAnimation = AnimationUtils.loadAnimation(this, R.anim.abc_fade_out);
            slide_out.setDuration(500);
            animationSet.addAnimation(menuAnimation);
            animationSet.addAnimation(slide_out);

            btn1.startAnimation(animationSet);
            btn2.startAnimation(animationSet);
            btn3.startAnimation(animationSet);
            btn4.startAnimation(animationSet);
            tv1.startAnimation(animationSet);
            tv2.startAnimation(animationSet);
            tv3.startAnimation(animationSet);
            tv4.startAnimation(animationSet);

            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                    btn4.setVisibility(View.GONE);

                    tv1.setVisibility(View.GONE);
                    tv2.setVisibility(View.GONE);
                    tv3.setVisibility(View.GONE);
                    tv4.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else {
            AnimationSet animationSet = new AnimationSet(false);
            menuAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_scale_up);
            Animation slide_in = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_bottom);
            slide_in.setDuration(200);
            Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
//For debugging

//            ///////////////////////
//            menuAnimation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    Log.d("Muzammil","menuAnimation Start");
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    Log.d("Muzammil","menuAnimation End");
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//                ///////////////////////
//            slide_in.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    Log.d("Muzammil","slide_in Start");
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    Log.d("Muzammil","slide_in End");
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//
//            ///////////////////////
//            fade_in.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    Log.d("Muzammil","fade_in Start");
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    Log.d("Muzammil","fade_in End");
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });


            animationSet.addAnimation(menuAnimation);
            animationSet.addAnimation(slide_in);
            animationSet.addAnimation(fade_in);

            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.VISIBLE);

            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.VISIBLE);

            btn1.startAnimation(animationSet);
            btn2.startAnimation(animationSet);
            btn3.startAnimation(animationSet);
            btn4.startAnimation(animationSet);

            tv1.startAnimation(animationSet);
            tv2.startAnimation(animationSet);
            tv3.startAnimation(animationSet);
            tv4.startAnimation(animationSet);
        }
    }
}
