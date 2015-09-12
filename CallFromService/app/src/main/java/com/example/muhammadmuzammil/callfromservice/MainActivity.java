package com.example.muhammadmuzammil.callfromservice;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    boolean callFromApp=true;
    boolean callFromOffHook=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("MuzammilQadri", "in onCreate");
        setContentView(R.layout.activity_main);

        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final TelephonyManager telephoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);


        setUpTelephoneListenerAndOnSpeaker(audioManager, telephoneManager);

        startService(new Intent(MainActivity.this, MyService.class));
        finish();
    }

    private void setUpTelephoneListenerAndOnSpeaker(final AudioManager audioManager, final TelephonyManager telephoneManager) {
        telephoneManager.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

                switch (state) {
                    case TelephonyManager.CALL_STATE_OFFHOOK: //Call is established
                        if (callFromApp) {
                            callFromApp = false;
                            callFromOffHook = true;

                            Log.v("MuzammilQadri", "in CALL_STATE_OFFHOOK, state: " + audioManager.isSpeakerphoneOn());
                            //Activate loudspeaker\
                            audioManager.setMode(AudioManager.MODE_IN_CALL);
                            audioManager.setSpeakerphoneOn(true);   //On Speaker
                            Log.v("MuzammilQadri", "in CALL_STATE_OFFHOOK, state: " + audioManager.isSpeakerphoneOn());
                        }
                        break;

                    case TelephonyManager.CALL_STATE_IDLE: //Call is finished
                        if (callFromOffHook) {
                            callFromOffHook=false;
                            Log.v("MuzammilQadri", "in CALL_STATE_IDLE state: " + audioManager.isSpeakerphoneOn());
                            audioManager.setMode(AudioManager.MODE_NORMAL); //Deactivate loudspeaker
                            audioManager.setSpeakerphoneOn(false);   //Off Speaker
                            telephoneManager.listen(this, PhoneStateListener.LISTEN_NONE); // Remove listener
                            Log.v("MuzammilQadri", "in CALL_STATE_IDLE state: " + audioManager.isSpeakerphoneOn());
                        }
                        break;
                }

            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }

}
