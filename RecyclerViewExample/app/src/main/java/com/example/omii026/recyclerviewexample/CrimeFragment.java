package com.example.omii026.recyclerviewexample;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Omii026 on 1/5/2016.
 */
public class CrimeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_crime,container,false);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        updateUI();
        mTitleField = (EditText) view.findViewById(R.id.crim_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = (Button) view.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });


        return view;
       }

    private void updateUI() {

    }

    private class CrimeHolder extends RecyclerView.ViewHolder{

        private Crime mCrime;
//        public TextView mTextView;
        private TextView mTitle;
        private TextView mDate;
        private CheckBox mSolved;

        public CrimeHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.list_item_title);
            mDate = (TextView) itemView.findViewById(R.id.list_item_date);
            mSolved = (CheckBox) itemView.findViewById(R.id.solve_check);
        }

        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitle.setText(mCrime.getTitle());
            mTitle.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());

        }


    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_crime_item,parent,false);

            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {

            Crime crime = mCrimes.get(position);
//            holder.mTextView.setText(crime.getTitle());
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
