package muzammil.muhammad.slidingpannel;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.Toast;

//TODO: Search "unable to click a layout after translation animation"

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View mView = findViewById(R.id.pannel);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Pannel Clicked..",Toast.LENGTH_LONG).show();
            }
        });


        Button button = (Button) findViewById(R.id.button);
        final View cover = findViewById(R.id.cover);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animationSet = new AnimationSet(false);
                Animation animation2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.abc_fade_in);
                Animation animation3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.abc_slide_in_bottom);
                animation3.setDuration(200);

                animationSet.addAnimation(animation2);
                animationSet.addAnimation(animation3);

                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mView.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        cover.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                mView.startAnimation(animationSet);

            }
        });

        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Cover Clicked..",Toast.LENGTH_LONG).show();
                AnimationSet animationSet = new AnimationSet(false);
                Animation animation2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.abc_fade_out);
                Animation animation3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.abc_slide_out_bottom);
                animation2.setDuration(300);
                animation3.setDuration(200);

                animationSet.addAnimation(animation2);
                animationSet.addAnimation(animation3);

                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        cover.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mView.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                mView.startAnimation(animationSet);

            }
        });
    }
}
