package com.example.muhammadmuzammil.fragmentcommunication;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;


import com.example.muhammadmuzammil.fragmentcommunication.FragmentA;
import com.example.muhammadmuzammil.fragmentcommunication.FragmentA.TextChangeListener;
import com.example.muhammadmuzammil.fragmentcommunication.FragmentB;

public class ActivityAB extends FragmentActivity {

    FragmentA fragmentA;
    FragmentB fragmentB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_ab);

        FragmentManager manager = getSupportFragmentManager();
        fragmentA = (FragmentA) manager.findFragmentById(R.id.fragmentA);
        fragmentB = (FragmentB) manager.findFragmentById(R.id.fragmentB);

        Log.v("Muz","in before Listener");
        fragmentA.setTextChangeListener(new TextChangeListener() {
            {
                Log.v("Muz","In MainActivity");
            }
            @Override
            public void onTextChange(CharSequence newText) {
                Log.v("Muz","In onTextChange");
                fragmentB.updateTextValue(newText);
            }
        });

    }

}