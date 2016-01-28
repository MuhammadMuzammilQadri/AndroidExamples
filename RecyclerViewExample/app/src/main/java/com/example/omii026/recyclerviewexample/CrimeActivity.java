package com.example.omii026.recyclerviewexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
       return new CrimeFragment();
            }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
////        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
//
////        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//
//        FragmentManager fm = getSupportFragmentManager();
//        android.support.v4.app.Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//        if(fragment == null){
//            fragment = new CrimeFragment();
//            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
//        }
//
//
////      getSupportFragmentManager().beginTransaction().add(R.id.container,new CrimeFragment()).commit();
//
//    }


}
