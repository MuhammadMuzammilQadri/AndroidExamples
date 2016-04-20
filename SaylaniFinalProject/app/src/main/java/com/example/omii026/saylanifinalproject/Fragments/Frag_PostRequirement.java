package com.example.omii026.saylanifinalproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.omii026.saylanifinalproject.FirebaseHandlers.FirebaseHandler;
import com.example.omii026.saylanifinalproject.Modles.Post;
import com.example.omii026.saylanifinalproject.R;
import com.example.omii026.saylanifinalproject.app;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class Frag_PostRequirement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText ed_unit,ed_comment,ed_contact;
    private Button btnPost;
    private Spinner bloodSpinner,unitsSpinner,urgencySpinner,countrySpinner,stateSpinner,citySpinner,hospitalSpinner,relationSpinner;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Frag_PostRequirement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_PostRequirement.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_PostRequirement newInstance(String param1, String param2) {
        Frag_PostRequirement fragment = new Frag_PostRequirement();
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
        View rootView = inflater.inflate(R.layout.fragment_frag__post_requirement, container, false);
        final Firebase ref = new Firebase("https://finalappss.firebaseio.com/");

        ed_unit = (EditText) rootView.findViewById(R.id.editText_units);
        ed_contact = (EditText) rootView.findViewById(R.id.editText_contact);
        ed_comment = (EditText) rootView.findViewById(R.id.edComment);

        bloodSpinner = (Spinner) rootView.findViewById(R.id.bloodGroup_spinner);
        urgencySpinner = (Spinner) rootView.findViewById(R.id.urgency_spinner);
        countrySpinner = (Spinner) rootView.findViewById(R.id.country_spinner);
        stateSpinner = (Spinner) rootView.findViewById(R.id.state_spinner);
        citySpinner = (Spinner) rootView.findViewById(R.id.city_spinner);
        hospitalSpinner = (Spinner) rootView.findViewById(R.id.hospital_spinner);
        relationSpinner = (Spinner) rootView.findViewById(R.id.relation_spinner);

//        bloodGroup
        List<String> bloodcategories = new ArrayList<String>();
        bloodcategories.add("A+");
        bloodcategories.add("B");
        bloodcategories.add("C");
        bloodcategories.add("O");
        ArrayAdapter<String> data1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bloodcategories);
        data1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodSpinner.setAdapter(data1);

//      urgency
        List<String> urgencyCategories = new ArrayList<String>();
        urgencyCategories.add("within 1 week");
        urgencyCategories.add("with in a day");
        urgencyCategories.add("within 6 hours");
        urgencyCategories.add("within an hour");
        ArrayAdapter<String> data2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, urgencyCategories);
        data2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        urgencySpinner.setAdapter(data2);

//        country
        List<String> countryCategories = new ArrayList<String>();
        countryCategories.add("Pakistan");
        countryCategories.add("Canada");
        countryCategories.add("Us");
        ArrayAdapter<String> data3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, countryCategories);
        data3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(data3);

//        state
        List<String> stateCategories = new ArrayList<String>();
        stateCategories.add("Sindh");
        stateCategories.add("Punjab");
        ArrayAdapter<String> data4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stateCategories);
        data4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(data4);
//        city
        List<String> cityCategories = new ArrayList<String>();
        cityCategories.add("Karachi");
        cityCategories.add("Torento");
        cityCategories.add("new york");
        ArrayAdapter<String> data5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cityCategories);
        data5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(data5);

//        hospital
        List<String> hospitalCategories = new ArrayList<String>();
        hospitalCategories.add("Agha khan");
        hospitalCategories.add("Walika");
        hospitalCategories.add("Zia ud deen");
        hospitalCategories.add("Liaquat");
        ArrayAdapter<String> data6 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, hospitalCategories);
        data6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospitalSpinner.setAdapter(data6);

//        relation
        List<String> relationCategories = new ArrayList<String>();
        relationCategories.add("Father");
        relationCategories.add("Brother");
        relationCategories.add("Sister");
        relationCategories.add("Friend");
        ArrayAdapter<String> data7 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cityCategories);
        data7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationSpinner.setAdapter(data7);

        ed_comment = (EditText) rootView.findViewById(R.id.edComment);
        btnPost = (Button) rootView.findViewById(R.id.postBtn);

        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.commentEd);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ed_comment.setFocusable(true);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inst =  ed_comment.getText().toString().trim();
                String contact = ed_contact.getText().toString().trim();
                String units = ed_unit.getText().toString().trim();
                String blood = (String) bloodSpinner.getSelectedItem();
                String urgency = (String) urgencySpinner.getSelectedItem();
                String country = (String) countrySpinner.getSelectedItem();
                String city = (String) citySpinner.getSelectedItem();
                String hospital = (String) hospitalSpinner.getSelectedItem();
                String relation = (String) relationSpinner.getSelectedItem();
                String state = (String) stateSpinner.getSelectedItem();

                if(!(inst.equals("") && contact.equals("") && units.equals(""))){

                    Post post = new Post(blood,units,urgency,country,state,city,hospital,relation,contact,inst);

                    Log.d("umair",""+app.getUser().getUserId());
                    Firebase reff = ref.child("posts").child(app.getUser().getUserId()).push();
                    post.setUserName(app.getUser().getFirstName());
                    reff.setValue(post
                            ,new Firebase.CompletionListener(){

                                @Override
                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                    if(firebaseError == null){
                                        Toast.makeText(getActivity(),"posted",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getActivity(),""+firebaseError.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{
                    Toast.makeText(getActivity(),"fields are emplt",Toast.LENGTH_SHORT).show();
                }


            }
        });


        return rootView;
    }

}
