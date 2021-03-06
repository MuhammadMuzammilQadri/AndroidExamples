package com.example.omii026.example_recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

//        layoutManager = new LinearLayoutManager(this);
        layoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new Adapter(getApplicationContext(),getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        ((Adapter)mAdapter).setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(getApplicationContext(),"T: "+position,Toast.LENGTH_SHORT).show();
            }
        });
    }


    private ArrayList<ObjectData> getDataSet() {
        ArrayList<ObjectData> list = new ArrayList<>();
        for(int i=0; i<30; i++){
            ObjectData data = new ObjectData("Title no# " + i,true);
            list.add(data);
        }
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();

}

}