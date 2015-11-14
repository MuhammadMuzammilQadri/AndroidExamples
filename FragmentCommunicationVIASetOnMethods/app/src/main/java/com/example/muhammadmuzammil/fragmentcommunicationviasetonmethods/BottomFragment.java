package com.example.muhammadmuzammil.fragmentcommunicationviasetonmethods;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BottomFragment extends Fragment implements MainActivity.OnActivityInteractionListener{

    private int counterValue;
    private TextView textView;


    public BottomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_bottom, container, false);
        textView= (TextView) rootView.findViewById(R.id.counter);
        return rootView;
    }





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    @Override
    public void incrementCounter(){
        Toast.makeText(getActivity(),"Incrementing Counter",Toast.LENGTH_SHORT).show();
        textView.setText(String.valueOf(++counterValue));
    }

    @Override
    public void decrementCounter(){
        Toast.makeText(getActivity(),"Decrementing Counter",Toast.LENGTH_SHORT).show();
        textView.setText(String.valueOf(--counterValue));
    }

}

