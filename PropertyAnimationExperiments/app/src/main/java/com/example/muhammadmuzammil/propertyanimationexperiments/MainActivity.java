package com.example.muhammadmuzammil.propertyanimationexperiments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView textView;
    private float actualY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Typeface typeface = Typeface.createFromFile(R.raw.myfont);
        Typeface typeface= Typeface.createFromAsset(getAssets(), "fonts/myfont.TTF");
        textView = (TextView) findViewById(R.id.textview);
        Button button = (Button) findViewById(R.id.button);
        textView.setTypeface(typeface);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                implementAnimation(textView);
            }
        });
    }

    private void implementAnimation( final TextView textView) {
//        Log.d("xyz", "textView getHeight: "+textView.getHeight());
//        Log.d("xyz", "textView getY: "+textView.getY());
//        Log.d("xyz", "textView getTranslationY: "+textView.getTranslationY());
//
//        Log.d("xyz", "rootView getMeasuredHeight: "+textView.getRootView().getMeasuredHeight());
//        Log.d("xyz", "rootView getHeight: "+textView.getRootView().getHeight());
//        Log.d("xyz", "rootView getWidth: "+textView.getRootView().getWidth());
//        Log.d("xyz", "rootView getY: "+textView.getRootView().getY());

        if (actualY == 0)
        actualY = textView.getY();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "y", -1000, 1000);
        objectAnimator.setDuration(600);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                implementAnimation2(textView);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectAnimator.start();

    }

    private void implementAnimation2(TextView textView) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "translationY", -1000);
        objectAnimator.setDuration(600);
        objectAnimator.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
