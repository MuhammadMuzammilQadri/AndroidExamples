package com.example.adnan.panavolley;

import android.app.Application;
import android.content.Context;

/**
 * Created by Adnan on 10/22/2015.
 */
public class myApplication extends Application {
    private static myApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static myApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();

    }
}
