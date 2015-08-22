package muzammil.muhammad.serviceexampleforsaylaniclass;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {


    private final CountDownTimer countDownTimer;
    private long currentSeconds;
    private long counter=0;
    private MyBinder myBinder= new MyBinder();

    public MyService() {

        Log.d("MuzammilQadri", "in MyService()");

       countDownTimer= new CountDownTimer(7000, 1000) {
            public void onTick(long millisUntilFinished) {
                currentSeconds= (7000-millisUntilFinished)/1000;    //just for debugging
                Log.d("MuzammilQadri",""+ millisUntilFinished + " "+currentSeconds+ " "+ ++counter);

            }

            public void onFinish() {
                this.start();
            }
        };

        countDownTimer.start();


    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MuzammilQadri", "in onBind()");
        return myBinder;
    }

    public long getCurrentSeconds() {
        Log.d("MuzammilQadri", "in getCurrentSeconds()");
        return counter;
    }

    public class MyBinder extends Binder{
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
