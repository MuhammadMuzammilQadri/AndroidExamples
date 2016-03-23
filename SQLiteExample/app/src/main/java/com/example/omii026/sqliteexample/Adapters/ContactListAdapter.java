package com.example.omii026.sqliteexample.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.omii026.sqliteexample.Database.DatabaseHandler;
import com.example.omii026.sqliteexample.R;
import com.example.omii026.sqliteexample.Classes.Contact;

import java.util.ArrayList;

/**
 * Created by Omii026 on 3/23/2016.
 */
public class ContactListAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Contact> data;
    private int resource;


    public ContactListAdapter(Context context, int resource,ArrayList<Contact> contact) {
        super(context, resource);
        this.context = context;
        this.data = contact;
        this.resource = resource;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(resource,parent,false);
        }
        ((TextView) convertView.findViewById(R.id.tvName)).setText(data.get(position).getName());
        ((TextView) convertView.findViewById(R.id.tvPhone)).setText("phone: "+data.get(position).getPhoneNumber());
        ((CheckBox) convertView.findViewById(R.id.checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    DatabaseHandler.getInstance().deleteContact(data.get(position));
                    remove(data.get(position));
                    compoundButton.setChecked(false);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public void add(Contact object) {
        super.add(object);
        data.add(object);
        notifyDataSetChanged();
    }

    @Override
    public void remove(Contact object) {
        super.remove(object);
        data.remove(object);
        notifyDataSetChanged();
    }

    class ViewHolder{

    }
}
