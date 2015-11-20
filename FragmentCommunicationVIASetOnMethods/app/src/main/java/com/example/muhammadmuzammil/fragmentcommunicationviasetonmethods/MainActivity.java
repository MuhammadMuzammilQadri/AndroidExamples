package com.example.muhammadmuzammil.fragmentcommunicationviasetonmethods;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

                                                    //Communication is as:
                                        //TopFragment - Activity - BottomFragment
                                                            //
                                //BottomFragment Again cant call any method in the top fragment,
                                   // because CYCLIC INHERITANCE error will be generated.

public class MainActivity extends ActionBarActivity implements TopFragment.OnFragmentInteractionListener{

    //For maintaining the single objects of both fragments,
    // in case of the later use of any fragment..
    Fragment topFragment= new TopFragment();
    Fragment bottomFragment= new BottomFragment();
       TopFragment myTopFragment= (TopFragment) topFragment;

    //beacause bottomFragment implements OnActivityInteractionListener..
    OnActivityInteractionListener mListener= (OnActivityInteractionListener) bottomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.topfragmentcontainer, topFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.bottomfragmentcontainer, bottomFragment).commit();

    }

    @Override
    public void onIncrementButtonClicked() {
        mListener.incrementCounter();
    }

    @Override
    public void onDecrementButtonClicked() {
        mListener.decrementCounter();
    }


    public interface OnActivityInteractionListener {
        // TODO: Update argument type and name
        public void incrementCounter();
        public void decrementCounter();

    }
}
