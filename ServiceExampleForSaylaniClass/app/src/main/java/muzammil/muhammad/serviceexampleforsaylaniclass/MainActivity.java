package muzammil.muhammad.serviceexampleforsaylaniclass;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private MyService mBoundService;
    private boolean isServiceBounded;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MuzammilQadri", "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.textView);

    }

    public void start(View view){
        Log.d("MuzammilQadri", "in start()");
        Intent intent=new Intent(MainActivity.this,MyService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        textView.setText("Service Started..");
        view.setEnabled(false);

    }

    public void show(View view){
        Log.d("MuzammilQadri", "in show()");
        if(isServiceBounded)
        textView.setText(""+mBoundService.getCurrentSeconds());
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MuzammilQadri", "in onServiceDisconnected()");
            isServiceBounded=false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.d("MuzammilQadri", "in onServiceConnected()");
            MyService.MyBinder myBinder = (MyService.MyBinder) service;
            mBoundService = myBinder.getService();
            isServiceBounded=true;

        }
    };
}
