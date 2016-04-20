package com.example.omii026.saylanifinalproject.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.omii026.saylanifinalproject.Adapters.PostAdapter;
import com.example.omii026.saylanifinalproject.Modles.Post;
import com.example.omii026.saylanifinalproject.R;
import com.example.omii026.saylanifinalproject.app;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Frag_MainHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Firebase ref;
    private PostAdapter postAdapter;


    public Frag_MainHome() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Frag_MainHome newInstance(String param1, String param2) {
        Frag_MainHome fragment = new Frag_MainHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_frag__main_home,container,false);
          ref = new Firebase("https://finalappss.firebaseio.com/");
//        ListView listView = (ListView) rootview.findViewById(R.id.postList);
//         postAdapter = new PostAdapter(getActivity(),new ArrayList<Post>());
//        listView.setAdapter(postAdapter);
//        getData();

        return rootview;
    }

    public void getData() {

    ref.child("posts").child(app.getUser().getFirstName()).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getValue() != null) {
                Post postData = dataSnapshot.getValue(Post.class);
                postData.setUserName(app.getUser().getUserName());
                postAdapter.add(postData);
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    });

    }
}
