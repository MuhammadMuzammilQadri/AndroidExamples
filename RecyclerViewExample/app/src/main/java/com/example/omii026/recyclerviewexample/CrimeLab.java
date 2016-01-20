package com.example.omii026.recyclerviewexample;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Omii026 on 1/5/2016.
 */
public class CrimeLab {

        private static CrimeLab sCrimeLab;
        private List<Crime> mCrimes;

        public static CrimeLab get(Context context) {
            if (sCrimeLab == null) {
                sCrimeLab = new CrimeLab(context);
            }
            return sCrimeLab;
        }
        private CrimeLab(Context context) {
            mCrimes = new ArrayList<>();
            for(int i=0;i<100;i++){
                Crime crime = new Crime();
                crime.setTitle("Crime #"+i);
                mCrimes.add(crime);
            }

        }
        public List<Crime> getCrimes() {
            return mCrimes;
        }
        public Crime getCrime(UUID id) {
            for (Crime crime : mCrimes) {
                if (crime.getmUUID().equals(id)) {
                    return crime;
                }
            }
            return null;
        }
    }

