package com.example.umairomii.firebaseauth.CloudMessaging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by umairomii on 5/27/16.
 */
public class InstanceIdService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String new_token = FirebaseInstanceId.getInstance().getToken();
        Log.d("InstanceIdService", "Refreshed token: " + new_token);
    }
}
