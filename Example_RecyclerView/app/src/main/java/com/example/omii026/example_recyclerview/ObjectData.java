package com.example.omii026.example_recyclerview;

import android.widget.TextView;

/**
 * Created by Omii026 on 1/5/2016.
 */
public class ObjectData {

    private String mTitle;
    private boolean mCheck;

    public ObjectData (String title,boolean check){
        mTitle = title;
        mCheck = check;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean isCheck() {
        return mCheck;
    }

    public void setCheck(boolean mCheck) {
        this.mCheck = mCheck;
    }
}
