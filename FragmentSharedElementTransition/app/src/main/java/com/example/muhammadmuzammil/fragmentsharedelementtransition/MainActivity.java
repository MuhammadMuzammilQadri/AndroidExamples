package com.example.muhammadmuzammil.fragmentsharedelementtransition;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new StartFragment()).addToBackStack(null).commit();
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

    public void changeFragment(View rootView, StartFragment startFragment,  EndFragment endFragment) {
        // Get access to or create instances to each fragment

// Check that the device is running lollipop

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // Inflate transitions to apply
            Transition changeTransform = TransitionInflater.from(MainActivity.this).
                    inflateTransition(R.transition.change_image_transform);
            Transition explodeTransform = TransitionInflater.from(MainActivity.this).
                    inflateTransition(android.R.transition.fade);

            // Setup exit transition on first fragment
            startFragment.setSharedElementReturnTransition(changeTransform);
            startFragment.setExitTransition(explodeTransform);
            startFragment.setAllowEnterTransitionOverlap(false);
            startFragment.setAllowReturnTransitionOverlap(false);

            // Setup enter transition on second fragment
            endFragment.setSharedElementEnterTransition(changeTransform);
            endFragment.setEnterTransition(explodeTransform);

            // Find the shared element (in Fragment A)
            View imageView =  rootView.findViewById(R.id.fragment_start_imageview);
            View textView = rootView.findViewById(R.id.fragment_start_heading);

            // Add second fragment by replacing first
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, endFragment)
                    .addToBackStack("transaction")
                    .addSharedElement(imageView, "myImage")
                    .addSharedElement(textView, "myHeading");

            // Apply the transaction
            ft.commit();
        }
        else{

            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.container, endFragment)
                    .addToBackStack("transaction")
                    .commit();

        }


    }

}
