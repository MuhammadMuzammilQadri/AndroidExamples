package com.panacloud.custompopupmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abdulazizniazi on 2/1/16.
 */
public class Adapter extends BaseAdapter {

    private ArrayList<String> list;
    private Context context;
    private LayoutInflater inflater;


    public Adapter(Context c,ArrayList<String> list){
        this.list = list;
        this.context = c;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
          view = inflater.inflate(R.layout.single_item1,viewGroup);
        }
        ((TextView) view.findViewById(R.id.textView)).setText(getItem(i));


        return null;
    }

    public void  Add(String s){
        list.add(s);
        notifyDataSetChanged();
    }
}
