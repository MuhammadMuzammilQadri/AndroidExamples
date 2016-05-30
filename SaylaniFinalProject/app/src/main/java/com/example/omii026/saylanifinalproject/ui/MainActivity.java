package com.example.omii026.saylanifinalproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omii026.saylanifinalproject.Modles.User;
import com.example.omii026.saylanifinalproject.R;
import com.example.omii026.saylanifinalproject.app;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button loginBtn;
    private Button sugnupBtn;
    private TextView createBtn;
    private EditText ed_username;
    private EditText ed_email;
    private EditText ed_password;
    private EditText ed_fname;
    private EditText ed_lname;
    private EditText ed_signup_email;
    private EditText ed_signup_password;
    private Spinner spinner;
    private String bloodGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);

        final Firebase ref = new Firebase("https://finalappss.firebaseio.com/");

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        loginBtn = (Button) findViewById(R.id.loginButton);
        sugnupBtn = (Button) findViewById(R.id.sugnup_button);
        createBtn = (TextView) findViewById(R.id.createAccount);

        ed_email = (EditText) findViewById(R.id.editText_email);
        ed_password = (EditText) findViewById(R.id.editText_password);

        ed_fname = (EditText) findViewById(R.id.editText_fname);
        ed_lname = (EditText) findViewById(R.id.editText_lname);
        ed_signup_email = (EditText) findViewById(R.id.editText_signup_email);
        ed_signup_password = (EditText) findViewById(R.id.editText_signup_password);
        spinner = (Spinner) findViewById(R.id.signup_spinner);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        ed_email.setText("u@a.com");
        ed_password.setText("123");
        final RelativeLayout loginLayout = (RelativeLayout) findViewById(R.id.layoutLogin);
        final RelativeLayout signupLayout = (RelativeLayout) findViewById(R.id.layoutSignUp);

        List<String> categories = new ArrayList<String>();
        categories.add("A");
        categories.add("B");
        categories.add("C");
        categories.add("O");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String uemail = ed_email.getText().toString().trim();
                String upass = ed_password.getText().toString().trim();

                ref.authWithPassword(uemail, upass, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(final AuthData authData) {
                        Toast.makeText(getApplicationContext(), "auth id-> " + authData.getUid(), Toast.LENGTH_SHORT).show();

                        Log.d("umair",""+authData.getUid());


                        ref.child("users").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                if(dataSnapshot.getValue() != null){
//                                    HashMap<String,Object> data = (HashMap<String, Object>) dataSnapshot.getValue();

                                    ref.child("users").child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener(){

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.getValue() != null) {
                                                HashMap<String, Object> userData = (HashMap<String, Object>) dataSnapshot.getValue();

                                                if (userData.get("userEmail").toString().equals(uemail)) {
                                                    User user = new User(userData.get("firstName").toString(), userData.get("lastName").toString(), userData.get("userEmail").toString(), userData.get("bloodGroup").toString());
                                                    user.setUserId(userData.get("firstName").toString());
                                                    app.setUser(user);
                                                    Toast.makeText(getApplicationContext(), "User Set", Toast.LENGTH_SHORT).show();

                                                    Intent i = new Intent(MainActivity.this,HomeActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(FirebaseError firebaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });



                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(), "auth id-> " +firebaseError.getMessage() , Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//            bloodGroup = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(),""+bloodGroup,Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginLayout.setVisibility(View.GONE);
                signupLayout.setVisibility(View.VISIBLE);
            }
        });


        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    // user is logged in
                } else {
                    // user is not logged in
                }
            }
        });

        sugnupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fname = ed_fname.getText().toString().trim();
                final String lname = ed_lname.getText().toString().trim();
                final String signup_email = ed_signup_email.getText().toString().trim();
                String signup_password = ed_signup_password.getText().toString().trim();
                bloodGroup = (String) spinner.getSelectedItem();
//                final String signup_bloodGroup = ed_signup_.getText().toString().trim();

                if(!(fname.equals("") && lname.equals("") && signup_email.equals("")
                        && signup_password.equals("") && bloodGroup.equals(""))){

                    ref.createUser(signup_email, signup_password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringObjectMap) {

                            User user = new User(fname,lname,signup_email,bloodGroup);
                            Toast.makeText(getApplicationContext(),"userCreated ",Toast.LENGTH_SHORT).show();

                            loginLayout.setVisibility(View.VISIBLE);
                            signupLayout.setVisibility(View.GONE);

                            Firebase reff = ref.child("users").push();
                            reff.setValue(user,new  Firebase.CompletionListener(){
                                @Override
                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                    Toast.makeText(getApplicationContext(),"data created",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(getApplicationContext(),""+firebaseError.getMessage().toString(),Toast.LENGTH_SHORT);
                        }
                    });
                }


            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }



//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
