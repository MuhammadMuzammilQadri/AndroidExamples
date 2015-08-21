package muzammil.muhammad.slidingpannel;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
                Toast.makeText(MainActivity.this,"Pannel Clicked..",Toast.LENGTH_LONG).show();            }
        });


        Button button = (Button) findViewById(R.id.button);
        final View cover = findViewById(R.id.cover);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment dialogFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.channel_select_panel);

                Animation dAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.dialog_bottom);
                dAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mView.bringToFront();
                        mView.requestFocus();
//                        cover.setVisibility(View.VISIBLE);

//                        mView.bringToFront();
//                        mView.requestFocus();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                dAnimation.setFillEnabled(true);
                dAnimation.setFillAfter(true);
                mView.startAnimation(dAnimation);

            }
        });

        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Cover Clicked..",Toast.LENGTH_LONG).show();
                Animation dAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.dialog_top);
                dAnimation.setFillEnabled(true);
                dAnimation.setFillAfter(true);
                mView.startAnimation(dAnimation);
//                cover.setVisibility(View.GONE);

            }


            }
        );


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
