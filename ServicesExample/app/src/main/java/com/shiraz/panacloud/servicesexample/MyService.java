package com.shiraz.panacloud.servicesexample;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    int a=1;
    Timer timer;
    TimerTask timerTask;
    private Handler handle = new Handler();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("onCreate:", "Service");
        Toast.makeText(getApplicationContext(),"onCreate",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy:", "Service");
        Toast.makeText(getApplicationContext(),"onDestroy",Toast.LENGTH_SHORT).show();

//        timer.cancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this,"in Service",Toast.LENGTH_SHORT).show();

        startTimer();


        return Service.START_STICKY;
    }

    private void startTimer() {
        timer = new Timer();
        initializeTimerTask();

        timer.schedule(timerTask,2000,4000);
    }

    private void initializeTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handle.post(new Runnable() {
                    @Override
                    public void run() {

                        Calendar calendar  = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MMMM:yy HH:mm:ss a");
                        String strDate = simpleDateFormat.format(calendar.getTime());
//                        Toast.makeText(getApplicationContext(),strDate,Toast.LENGTH_SHORT).show();

                    }
                });
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("onBind:","Service");

        throw new UnsupportedOperationException("Not yet implemented");
    }

}
