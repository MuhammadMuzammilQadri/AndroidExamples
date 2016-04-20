package com.example.omii026.sqliteexample.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.omii026.sqliteexample.Adapters.ContactListAdapter;
import com.example.omii026.sqliteexample.Database.DatabaseHandler;
import com.example.omii026.sqliteexample.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText contact_name,contact_phoneNo;
    Button create_btn;
    ListView contact_list;
    private DatabaseHandler databaseHandler;
    private ContactListAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
        setUpListeners();
        updateContactList();


    }

    private void initializeViews() {
        databaseHandler = new DatabaseHandler(MainActivity.this);

        contact_name = (EditText) findViewById(R.id.name);
        contact_phoneNo = (EditText) findViewById(R.id.phoneNo);
        create_btn = (Button) findViewById(R.id.createContact);
        contact_list = (ListView) findViewById(R.id.contactlist);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        adapter = new ContactListAdapter(MainActivity.this,R.layout.list_item,new ArrayList<Contact>());
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

//                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
//
//                if(checkBox.isChecked())
//                    checkBox.setChecked(false);
//                else
//                    checkBox.setChecked(true);

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
