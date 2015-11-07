package com.omii026.panacloud.broadcastexample;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity
        implements SmsReceiver.OnSmsReceivelistener{

    private TextView msgFrom;
    private TextView msgBody;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        registerReceiver(broadcastReceiver, new IntentFilter("SmsBroadcast"));




//        Intent intent = new Intent();
//        intent.setAction("Umair");
//        sendBroadcast(intent);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle i = intent.getExtras();
            String message = i.getString("message");
            String messageFrom = i.getString("messageFrom");

            Toast.makeText(context, "From:" + messageFrom + " .." + "Msg:" + message, Toast.LENGTH_SHORT).show();

            Log.d("received",""+message);
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSmsReceived(String msg, String num) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_view);

        msgFrom = (TextView) dialog.findViewById(R.id.msgFrom);
        msgBody = (TextView) dialog.findViewById(R.id.msgBody);



//        registerReceiver(,new IntentFilter("SmsBroadcast"));

//        dialog.setCancelable(false);
//        msgBody.setText(msg);
//        msgFrom.setText(num);
//
//        ((Button) dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();




    }
}
