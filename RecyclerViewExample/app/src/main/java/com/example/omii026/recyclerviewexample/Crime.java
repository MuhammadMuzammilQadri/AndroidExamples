package com.example.omii026.recyclerviewexample;

import android.util.Log;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Omii026 on 1/5/2016.
 */
public class Crime {

    private UUID mUUID;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime (){
        mUUID = mUUID.randomUUID();
        mDate = new Date();
        Log.d("UUID",""+ mUUID);
    }
    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }
    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public UUID getmUUID() {
        return mUUID;
    }

    public void setmUUID(UUID mUUID) {
        this.mUUID = mUUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }
}
