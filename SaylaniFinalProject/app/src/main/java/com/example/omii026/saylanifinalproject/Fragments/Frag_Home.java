package com.example.omii026.saylanifinalproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.omii026.saylanifinalproject.Adapters.PostAdapter;
import com.example.omii026.saylanifinalproject.Modles.Post;
import com.example.omii026.saylanifinalproject.R;
import com.example.omii026.saylanifinalproject.app;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Firebase ref;
    private PostAdapter postAdapter;

    private OnFragmentInteraction mListener;

    public Frag_Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_Home newInstance(String param1, String param2) {
        Frag_Home fragment = new Frag_Home();
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
        View rootview = inflater.inflate(R.layout.fragment_frag__home, container, false);
        Log.d("umair","onCreate frag_Home");
        ref = new Firebase("https://finalappss.firebaseio.com/");
        ListView listView = (ListView) rootview.findViewById(R.id.postList);
        postAdapter = new PostAdapter(getActivity(), new ArrayList<Post>());
        listView.setAdapter(postAdapter);
        getData();


        return rootview;

    }


    public interface OnFragmentInteraction{
        void postDetal(Post p);
    }
    //
    public void getData() {

        ref.child("posts").child(app.getUser().getUserId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    Log.d("umair",""+dataSnapshot.getKey());
                    String key = dataSnapshot.getKey();
                    ref.child("posts").child(app.getUser().getUserId()).child(key).addValueEventListener(new ValueEventListener() {
                        Post postData;
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {

                                    postData = dataSnapshot.getValue(Post.class);
//                                    postData.setUserName(app.getUser().getFirstName() +" "+app.getUser().getLastName());
                                    postAdapter.add(postData);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });


                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

}
