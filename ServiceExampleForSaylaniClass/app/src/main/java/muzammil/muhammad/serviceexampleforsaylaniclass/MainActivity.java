package muzammil.muhammad.serviceexampleforsaylaniclass;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView textView;
    private MyService mBoundService;
    private ServiceConnection mServiceConnection;
    private BroadcastReceiver serviceReceiver;
    private boolean isServiceBounded;
    IntentFilter serviceIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("D", "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        mServiceConnection = new ServiceConnection() {
            {
                Log.d("MuzammilQadri", "in Anonymous ServiceConnection()");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("MuzammilQadri", "in onServiceDisconnected()");
                isServiceBounded = false;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                Log.d("MuzammilQadri", "in onServiceConnected()");
                MyService.MyBinder myBinder = (MyService.MyBinder) service;
                mBoundService = myBinder.getService();
                isServiceBounded = true;

            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MuzammilQadri", "in onResume()");

        serviceIntentFilter = new IntentFilter("MyService_Broadcasting");
        serviceReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                if (intent != null) {
                    Log.d("MuzammilQadri", "in onReceive()");
                    updateUserInterfaceOnEachSecond();
                }
            }
        };

        if (isMyServiceRunning(MyService.class)) {    //CHeck if serviceis running or not..
            setupForAgainRunning();
        }
    }

    public void start(View view) {
        Log.d("MuzammilQadri", "in start()");
        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        textView.setText("Service Started..");
        view.setEnabled(false);
        findViewById(R.id.stop).setEnabled(true);
        findViewById(R.id.pause).setEnabled(true);
        registerReceiver(this.serviceReceiver, serviceIntentFilter);

    }

    public void stop(View view) {
        Log.d("MuzammilQadri", "in stop()");
        Intent intent = new Intent(MainActivity.this, MyService.class);
        textView.setText("Service Stoped\nat counter .." + mBoundService.getCurrentSeconds());
        unbindService(mServiceConnection);
        stopService(intent);
        view.setEnabled(false);
        findViewById(R.id.start).setEnabled(true);
        findViewById(R.id.pause).setEnabled(false);

    }

    public void pause(View view) {
        Log.d("MuzammilQadri", "in pause()");
        textView.setText("Counter Paused\nat counter .." + mBoundService.getCurrentSeconds());
//        unbindService(mServiceConnection);
        mBoundService.pauseCounter();
        view.setEnabled(false);
        findViewById(R.id.start).setEnabled(true);
        findViewById(R.id.stop).setEnabled(false);

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void setupForAgainRunning() {
        Log.d("MuzammilQadri", "in setupForAgainRunning()");
//        mBoundService=MyService.getInstance();

        findViewById(R.id.start).setEnabled(false);
        findViewById(R.id.stop).setEnabled(true);
        findViewById(R.id.pause).setEnabled(true);

        Intent intent = new Intent(MainActivity.this, MyService.class);
//        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        textView.setText("Service Started..");
        registerReceiver(this.serviceReceiver, serviceIntentFilter);

    }

    public void updateUserInterfaceOnEachSecond() {
        textView.setText("" + mBoundService.getCurrentSeconds());
    }
}
