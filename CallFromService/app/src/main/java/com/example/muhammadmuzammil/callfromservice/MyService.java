package com.example.muhammadmuzammil.callfromservice;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
        Log.v("MuzammilQadri", "in MyService Constructor");
    }

    @Override
    public int onStartCommand(Intent systemIntent, int flags, int startId) {
        Log.v("MuzammilQadri", "in onStartCommand");

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setPackage("com.android.phone"); //For calling from the native dialers
        intent.setData(Uri.parse("tel:03432115322"));
        intent.putExtra("simSlot", 1); //For sim 1
        intent.putExtra("com.android.phone.extra.slot", 1); //For sim 1
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
