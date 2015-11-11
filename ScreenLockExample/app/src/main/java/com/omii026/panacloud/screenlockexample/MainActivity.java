package com.omii026.panacloud.screenlockexample;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    DevicePolicyManager devicePolicyManager;
    ComponentName componentName;
    ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);


        componentName = new ComponentName(this, MyAdmin.class);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.lock)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                boolean active = devicePolicyManager.isAdminActive(componentName);
//
//                 if(active){
                      devicePolicyManager.lockNow();
//                    }

            }
        });

        ((Button) findViewById(R.id.enable)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN,componentName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"Explanation");
//                devicePolicyManager.lockNow();
                startActivity(intent);

            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
////        if(requestCode == 1 ){
////            Log.d("DeviceAdminSample", "Admin enabled!");
////        }else{
////            Log.d("DeviceAdminSample", "Admin disable!");
////
////        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }

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
}
