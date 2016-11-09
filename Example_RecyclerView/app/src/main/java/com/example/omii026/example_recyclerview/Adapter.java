package com.example.omii026.example_recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Omii026 on 1/5/2016.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.DataHolder> {

    private static OnItemClickListener onItemClickListener;
    private ArrayList<ObjectData> mDataSet;
    private Context context;
    private int selectedIndex;

    Adapter(Context context,ArrayList<ObjectData> list){
        mDataSet = list;
        this.context = context;
        selectedIndex = -1;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item,parent,false);

        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, final int position) {
        holder.title.setText(mDataSet.get(position).getmTitle());
//        holder.check.setEnabled(mDataSet.get(position).isCheck());
        holder.check.setChecked(mDataSet.get(position).isCheck());
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSet.get(position).setCheck(!mDataSet.get(position).isCheck());
            }
        });

        if(selectedIndex!= -1 && position == selectedIndex)
        {
            holder.main.setBackgroundColor(Color.RED);
        }
        else
        {
            holder.main.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addItem(ObjectData dataObj, int index) {
        mDataSet.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }

    public void setSelectedIndex(int ind)
    {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        CheckBox check;
        View main;


        public DataHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            check = (CheckBox) itemView.findViewById(R.id.check);
            main =  itemView.findViewById(R.id.main);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

//            Toast.makeText(context,""+title,Toast.LENGTH_SHORT).show();
            int position = getLayoutPosition();
            setSelectedIndex(position);
            onItemClickListener.onItemClick(position,view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    interface   OnItemClickListener {
        void onItemClick(int position,View view);
    }
}
