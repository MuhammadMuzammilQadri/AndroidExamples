package com.example.omii026.saylanifinalproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.omii026.saylanifinalproject.Modles.Post;
import com.example.omii026.saylanifinalproject.R;

import java.util.ArrayList;

/**
 * Created by Omii026 on 3/13/2016.
 */
public class PostAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    ArrayList<Post> listData;

    public PostAdapter(Context context,ArrayList<Post> listData){
        this.context = context;
        this.listData = listData;
         inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rootView = inflater.inflate(R.layout.post_layout,viewGroup,false);

        ((TextView) rootView.findViewById(R.id.userName)).setText(listData.get(i).getUserName());
        ((TextView) rootView.findViewById(R.id.unitsDesc)).setText(listData.get(i).getUnitsRequired());
        ((TextView) rootView.findViewById(R.id.urgent_value)).setText(listData.get(i).getUrgency());
        ((TextView) rootView.findViewById(R.id.contactNum)).setText(listData.get(i).getContactNo());
        ((TextView) rootView.findViewById(R.id.addIns_Desc)).setText(listData.get(i).getAdditionalInstruction());
        ((TextView) rootView.findViewById(R.id.VolunterCount)).setText("2");
        ((TextView) rootView.findViewById(R.id.currentRequirement_num)).setText("5");

        ((Button) rootView.findViewById(R.id.Vol)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
        ((Button) rootView.findViewById(R.id.comm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rootView;

    }


    public void add(Post s){
        listData.add(s);
        notifyDataSetChanged();
    }
}
