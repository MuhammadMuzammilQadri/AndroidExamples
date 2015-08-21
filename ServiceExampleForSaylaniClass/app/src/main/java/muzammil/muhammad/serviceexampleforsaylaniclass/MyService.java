package muzammil.muhammad.serviceexampleforsaylaniclass;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static MyService sInstance;
    private CountDownTimer countDownTimer;
    private long currentSeconds;
    private long counter=0;
    private MyBinder myBinder= new MyBinder();
    private boolean isActivityStarted;
    private Intent intentForBroadCastReceiver;

    public MyService() {

        Log.d("MuzammilQadri", "in MyService()");

    }

    public void pauseCounter(){
        if(countDownTimer!= null)
            countDownTimer.cancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MuzammilQadri", "in onStartCommand()");

        countDownTimer= new CountDownTimer(60000, 1000) {
            {
                Log.d("MuzammilQadri", "in Anonymous countDownTimer()");
            }
            public void onTick(long millisUntilFinished) {
                counter++;

                if(isActivityStarted)
                {
                    Log.d("MuzammilQadri", "in onTick if clause");
                    MyService.this.sendBroadcast(intentForBroadCastReceiver);
                }

                //just for debugging
                currentSeconds= (millisUntilFinished)/1000;
                Log.d("MuzammilQadri", " "+currentSeconds+ " "+ counter);

            }

            public void onFinish() {
                this.start();
            }
        };
        Log.d("MuzammilQadri", "Below countDownTimer in onStartCommand()");

        countDownTimer.start();

        Log.d("MuzammilQadri", "Below countDownTimer.start() in onStartCommand()");

//        stopSelf();
//        return START_NOT_STICKY;
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d("MuzammilQadri", "in onCreate()");
        sInstance=this;
        super.onCreate();
    }

    public static MyService getInstance(){
        return sInstance;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MuzammilQadri", "in onBind()");
        isActivityStarted=true;

        intentForBroadCastReceiver= new Intent();
        intentForBroadCastReceiver.setAction("MyService_Broadcasting");

        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("MuzammilQadri", "in onUnbind()");
        isActivityStarted=false;
        return super.onUnbind(intent);
    }

    public long getCurrentSeconds() {
        Log.d("MuzammilQadri", "in getCurrentSeconds()");
        return counter;
    }

    public class MyBinder extends Binder{
        {
            Log.d("MuzammilQadri", "in Class MyBinder()");
        }
        MyService getService() {
            Log.d("MuzammilQadri", "in getService()");
            return MyService.this;
        }
    }

    @Override
    public void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();

    }

}
