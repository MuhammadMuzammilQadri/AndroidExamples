package com.example.omii026.sqliteexample.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.omii026.sqliteexample.R;
import com.example.omii026.sqliteexample.ui.Contact;

import java.util.ArrayList;
import java.util.List;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(resource,parent,false);
        }
        ((TextView) convertView.findViewById(R.id.tvName)).setText(data.get(position).getName());
        ((TextView) convertView.findViewById(R.id.tvPhone)).setText("No. "+data.get(position).getPhoneNumber());

        return convertView;
    }

    @Override
    public void add(Contact object) {
        super.add(object);
        data.add(object);
        notifyDataSetChanged();
    }

    class ViewHolder{

    }
}
