package com.az3ez.samplemeezan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    TextView mTextView;
    JSONObject jsonObject;
    EditText customerID, customerPass;
    private String TAG = "Response";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpRequestHandler.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new DetailFragment()).addToBackStack("").commit();


//        Button loginButton = (Button) findViewById(R.id.btn_submit);
//        customerID = (EditText) findViewById(R.id.customer_id);
//        customerPass = (EditText) findViewById(R.id.pass);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String id = customerID.getText().toString();
//                String pass = customerPass.getText().toString();
//                AuthenticationService.login(id, pass, new ServiceListener() {
//                    @Override
//                    public void success(Object obj) {
//                        Log.d(TAG, "" + obj);
//                        DetailFragment fragment = new DetailFragment();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("OBJECT", "" + obj.toString());
//                        fragment.setArguments(bundle);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("").commit();
//
//                    }
//
//                    @Override
//                    public void error(ServiceError serviceError) {
//                        Log.d(TAG, "Error: " + serviceError);
//
//                    }
//                });
//            }
//        });
    }


}
