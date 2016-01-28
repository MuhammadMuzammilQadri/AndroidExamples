package com.example.omii026.example_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Omii026 on 1/5/2016.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.DataHolder> {

    private static MyClickListener myClickListener;
    private ArrayList<ObjectData> mDataSet;
    private Context context;
    Adapter(Context context,ArrayList<ObjectData> list){
        mDataSet = list;
        this.context = context;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item,parent,false);

        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        holder.title.setText(mDataSet.get(position).getmTitle());
        holder.check.setEnabled(mDataSet.get(position).isCheck());
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

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        CheckBox check;

        public DataHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            check = (CheckBox) itemView.findViewById(R.id.check);
//            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            Toast.makeText(context,""+title,Toast.LENGTH_SHORT).show();
//            myClickListener.onItemClick(getPosition(),view);
        }
    }

    public void setOnClickListener(MyClickListener myClickListener){
        this.myClickListener = myClickListener;
    }

    interface MyClickListener{
        void onItemClick(int position,View view);
    }
}
