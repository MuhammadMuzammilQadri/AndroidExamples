package com.example.muhammadmuzammil.callblocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Muhammad Muzammil on 8/11/2016.
 */


public class PhoneCallStateListener extends PhoneStateListener {

    private Context context;
    public PhoneCallStateListener(Context context){
        this.context = context;
    }


    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);

        switch (state) {

            case TelephonyManager.CALL_STATE_RINGING:

                String block_number = BlockNumbersHandler.getInstance().getBlockedNumber();
                System.out.println("block_number: "+block_number);

                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                //Turn ON the mute
                audioManager.setStreamMute(AudioManager.STREAM_RING, true);
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
//                    Toast.makeText(context, "Incoming Number: "+incomingNumber +", Blocked Number: "+block_number, Toast.LENGTH_SHORT).show();
                    Class clazz = Class.forName(telephonyManager.getClass().getName());
                    Method method = clazz.getDeclaredMethod("getITelephony");
                    method.setAccessible(true);
                    ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);
                    //Checking incoming call number

                    if (incomingNumber.equalsIgnoreCase("+1"+block_number) ||
                            incomingNumber.equalsIgnoreCase("1"+block_number) ||
                            incomingNumber.equalsIgnoreCase(block_number)) {
                        //telephonyService.silenceRinger();//Security exception problem
                        telephonyService = (ITelephony) method.invoke(telephonyManager);
                        telephonyService.silenceRinger();
                        telephonyService.endCall();
//                        System.out.println("Number blocked: " + block_number);
//                        Toast.makeText(context, "Number Forward", Toast.LENGTH_SHORT).show();

//                        try {
//                            telephonyService.answerRingingCall();
//                        } catch (RemoteException e) {
//                            Toast.makeText(context, "From answerRingingCall: "+e.toString(), Toast.LENGTH_SHORT).show();
//                            telephonyService = null;
//                            e.printStackTrace();
//                        }
//                        acceptCall();
//                        playMessage(telephonyService);

                    }
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //Turn OFF the mute
                audioManager.setStreamMute(AudioManager.STREAM_RING, false);
                break;
            case PhoneStateListener.LISTEN_CALL_STATE:

        }
        super.onCallStateChanged(state, incomingNumber);
    }

}