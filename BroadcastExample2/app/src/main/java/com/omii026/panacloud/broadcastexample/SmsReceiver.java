package com.omii026.panacloud.broadcastexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by panacloud on 11/5/15.
 */
public class SmsReceiver extends BroadcastReceiver {

    public static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ACTION)){
            Bundle bundle = intent.getExtras();

            if(bundle != null){

                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];

                for (int i = 0; i < pdus.length; i++){
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                for(SmsMessage message : messages){

                    String MessageFrom = message.getDisplayOriginatingAddress();
                    String MessageBody = message.getMessageBody();

                    Toast.makeText(context,"From:"+MessageFrom+" .."+"Msg:"+MessageBody,Toast.LENGTH_SHORT).show();
                }
             }
        }



    }
}
