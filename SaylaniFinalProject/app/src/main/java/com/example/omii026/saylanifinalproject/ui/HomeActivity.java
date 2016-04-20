package com.example.omii026.saylanifinalproject.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.omii026.saylanifinalproject.Fragments.Frag_Home;
import com.example.omii026.saylanifinalproject.Fragments.Frag_MainHome;
import com.example.omii026.saylanifinalproject.Fragments.Frag_MyRequest;
import com.example.omii026.saylanifinalproject.Fragments.Frag_Notification;
import com.example.omii026.saylanifinalproject.Fragments.Frag_PostDetail;
import com.example.omii026.saylanifinalproject.Fragments.Frag_PostRequirement;
import com.example.omii026.saylanifinalproject.Fragments.Frag_Setting;
import com.example.omii026.saylanifinalproject.Modles.Post;
import com.example.omii026.saylanifinalproject.R;
import com.example.omii026.saylanifinalproject.app;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
Frag_Home.OnFragmentInteraction{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new Frag_MainHome()).addToBackStack(null).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ((TextView) drawer.findViewById(R.id.appUserName)).setText(app.getUser().getFirstName()+" "+app.getUser().getLastName());
//        ((TextView) drawer.findViewById(R.id.appuserEmail)).setText(app.getUser().getUserEmail());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_activity2, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,new Frag_Home()).addToBackStack(null).commit();
            // Handle the camera action
        } else if (id == R.id.nav_request) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,new Frag_MyRequest()).addToBackStack(null).commit();
        } else if (id == R.id.nav_req) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,new Frag_PostRequirement()).addToBackStack(null).commit();
        } else if (id == R.id.nav_noti) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,new Frag_Notification()).addToBackStack(null).commit();
        } else if (id == R.id.set_setting) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,new Frag_Setting()).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void postDetal(Post p) {
        getSupportFragmentManager().beginTransaction().add(R.id.container,Frag_PostDetail.newInstance(p)).addToBackStack(null).commit();
    }
}
