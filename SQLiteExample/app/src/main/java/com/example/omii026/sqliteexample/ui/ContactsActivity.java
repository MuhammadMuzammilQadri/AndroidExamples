package com.example.omii026.sqliteexample.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.omii026.sqliteexample.Adapters.ContactListAdapter;
import com.example.omii026.sqliteexample.Classes.Contact;
import com.example.omii026.sqliteexample.Database.DatabaseHandler;
import com.example.omii026.sqliteexample.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText contact_name,contact_phoneNo;
    Button create_btn;
    ListView contact_list;
    private DatabaseHandler databaseHandler;
    private ContactListAdapter adapter;
    private FloatingActionButton fab;
    private android.support.design.widget.CoordinatorLayout layout;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
        setUpListeners();
        updateContactList();

        drawer.setScrimColor(Color.TRANSPARENT);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.d("silderOffset",""+slideOffset);
                Log.d("contact_list.getWidth",""+contact_list.getWidth());
                Log.d("transection_product",""+contact_list.getWidth()*slideOffset);
//                layout.setTranslationX(contact_list.getWidth()*slideOffset);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
        getMenuInflater().inflate(R.menu.contacts, menu);
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

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initializeViews() {
        databaseHandler = new DatabaseHandler(ContactsActivity.this);

        contact_name = (EditText) findViewById(R.id.name);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        contact_phoneNo = (EditText) findViewById(R.id.phoneNo);
        create_btn = (Button) findViewById(R.id.createContact);
        contact_list = (ListView) findViewById(R.id.contactList);
        fab = (FloatingActionButton) findViewById(R.id.fab);
         layout = (android.support.design.widget.CoordinatorLayout) findViewById(R.id.container_main);

        adapter = new ContactListAdapter(ContactsActivity.this,R.layout.list_item,new ArrayList<Contact>());
        contact_list.setAdapter(adapter);
    }

    private void setUpListeners() {
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = contact_name.getText().toString().trim();
                String phoneNo = contact_phoneNo.getText().toString().trim();
                if(!name.equals("") && !phoneNo.equals("")){
                    databaseHandler.addContact(new Contact(name,phoneNo));
                    adapter.add(new Contact(name,phoneNo));
                    Toast.makeText(getApplicationContext(),"added",Toast.LENGTH_SHORT).show();
                    contact_name.setText("");
                    contact_phoneNo.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"fields empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

       contact_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_SHORT).show();
           }
       });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void updateContactList() {
        List<Contact> contact = new ArrayList<>();
        contact = databaseHandler.getAllContacts();

        for (Contact cn : contact) {
            adapter.add(cn);
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }

}
