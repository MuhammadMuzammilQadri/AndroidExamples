package com.example.faiz.firebasekitafree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.ServerValue;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Firebase firebase;
    Firebase firebaseConnectedInfo;
    Firebase firebaseCounter;
    Firebase firebaseStatus;
    Firebase firebaseGroup;

    TextView onlineStatus;
    TextView countStatus;

    Button online;
    Button offline;
    Button increment;
    Button decrement;
    Button incrementTransaction;
    Button decrementTransaction;
    Button incrementTransactionGroup;
    Button decrementTransactionGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initFirebase();
        setOnClickListener();

    }

    private void initViews(){
        onlineStatus = (TextView) findViewById(R.id.status);
        countStatus = (TextView) findViewById(R.id.status2);

        online = (Button) findViewById(R.id.online);
        offline = (Button) findViewById(R.id.offline);
        increment = (Button) findViewById(R.id.inc_val);
        decrement = (Button) findViewById(R.id.dec_val);
        incrementTransaction = (Button) findViewById(R.id.inc_trans);
        decrementTransaction = (Button) findViewById(R.id.dec_trans);
        incrementTransactionGroup = (Button) findViewById(R.id.inc_trans_group);
        decrementTransactionGroup = (Button) findViewById(R.id.dec_trans_group);

    }

    private void initFirebase(){
        try {
            Firebase.setAndroidContext(getApplicationContext());
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
        } catch (RuntimeException e){
            e.printStackTrace();
        }

        firebase = new Firebase("https://test-employeeconnect.firebaseio.com/");
        firebaseConnectedInfo = new Firebase("https://test-employeeconnect.firebaseio.com/.info/connected");
        firebaseCounter = firebase.child("count");
        firebaseStatus = firebase.child("status");
        firebaseGroup = firebase.child("group");

        firebaseConnectedInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    onlineStatus.setText("online");
                    firebaseStatus.onDisconnect().setValue("disconnect", new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            firebaseStatus.setValue("online");
                        }
                    });
                } else {
                    onlineStatus.setText("offline");
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {

            }
        });

        firebaseCounter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                countStatus.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void setOnClickListener(){
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase.goOnline();
            }
        });
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase.goOffline();
            }
        });
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseCounter.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long count = (Long) dataSnapshot.getValue();
                        firebaseCounter.setValue(count+1);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
        incrementTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseCounter.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        if (mutableData == null){
                            mutableData.setValue(1);
                        } else {
                            mutableData.setValue((Long) mutableData.getValue() + 1);
                        }
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseCounter.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long count = (Long) dataSnapshot.getValue();
                        firebaseCounter.setValue(count-1);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });
        decrementTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseCounter.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        if (mutableData == null){
                            mutableData.setValue(1);
                        } else {
                            mutableData.setValue((Long) mutableData.getValue() - 1);
                        }
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        incrementTransactionGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseGroup.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        MutableData countData = (MutableData) mutableData.child("members-count");
                        MutableData timestampData = (MutableData) mutableData.child("timestamp");

                        countData.setValue((Long)countData.getValue()+1);
                        timestampData.setValue(ServerValue.TIMESTAMP);

                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        decrementTransactionGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseGroup.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        MutableData countData = (MutableData) mutableData.child("members-count");
                        MutableData timestampData = (MutableData) mutableData.child("timestamp");

                        countData.setValue((Long)countData.getValue()-1);
                        timestampData.setValue(ServerValue.TIMESTAMP);

                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
