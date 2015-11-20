package com.example.faiz.sqlliteexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.faiz.sqlliteexample.database.Datahandler;
import com.example.faiz.sqlliteexample.model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Datahandler datahandler = Datahandler.getInstance(this);

        datahandler.addContact(new Contact("faiz","5554165"));
        datahandler.addContact(new Contact("faiz2","464861"));
        datahandler.addContact(new Contact("faiz3","613864"));
        datahandler.addContact(new Contact("faiz4", "846868"));


        for (Contact contact : datahandler.getAllContacts())
            Log.i("sqldata", "loop "+contact.getName());

        Log.i("sqldata", "data fetch " + datahandler.getContact(2).getName());

        Contact c = datahandler.getContact(2);
         c.setName(c.getName()+"alter");
        datahandler.updateContact(c);
        Log.i("sqldata", "data updated " + datahandler.getContact(2).getName());

        datahandler.deleteContact(c);
        Log.i("sqldata", "data deleted " + datahandler.getContact(c.getID()));

    }
}
