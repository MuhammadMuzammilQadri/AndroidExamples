package com.example.omii026.wifi;

//http://stackoverflow.com/questions/20345155/android-receive-and-send-data-through-wifi-connection-to-hardware

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import java.net.UnknownHostException;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_IP = "192.168.0.104";
    private static final int SERVERPORT = 8000;
    private InetAddress serverAddr;
    private Socket socket;
    private volatile boolean stopWorker;
    private BufferedWriter wr;
    String value1,value2,sand;
    private Thread workerThread;

    TextView temp;
    private BufferedReader rea;
    private String ss;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         textView1 = (TextView) findViewById(R.id.textView1);
         textView2 = (TextView) findViewById(R.id.textView2);
         textView3 = (TextView) findViewById(R.id.textView3);
         textView4 = (TextView) findViewById(R.id.textView4);

         ss = "a0240000NOFINGERz";

        MyTask myTask = new MyTask();
        myTask.execute();
//        beginListenForData();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ss.length() == 17) {
                    if ((ss.substring(0, 1)).equals("a")) {
                        textView1.setText(ss.substring(1, 4));
                        textView2.setText(ss.substring(4, 8));
                        if ((ss.substring(8, 9)).equals("N")) {
                            textView3.setText(ss.substring(8, 12));
                            textView4.setText(ss.substring(12, 16));
                        }
                    }
                }


//                try {
//                    wr.write("ABCDEFGH");
//                    wr.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }
    void beginListenForData(){
        final Handler handler = new Handler();

        stopWorker = false;
        workerThread = new Thread(new Runnable(){
            public void run(){
                try{
                    serverAddr = InetAddress.getByName(SERVER_IP);
                    socket = new Socket(serverAddr, SERVERPORT);
                    wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    rea = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                }
                catch (IOException ex)
                {
                    stopWorker = true;
                }


                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try {
//     rea.reset();
//while ((charsRead = rea.read(buffer)) != -1) {
                        //   ss += new String(buffer).substring(0, charsRead);
                        //}



                        if(rea.ready()){
                            ss=rea.readLine();
                            Toast.makeText(getApplicationContext(),"connected", LENGTH_SHORT).show();
                        }
                        else{
//                            textView.setText("000");
//                            cal.setText("0000");
//                            spo.setText("0000");
//                            hb.setText("0000");

                        }
                        handler.post(new Runnable()
                        {
                            public void run()
                            {

                                textView1.setText(ss);
//                                if ((ss.substring(0, 1)).equals("a"))
//                                {
//                                    textView.setText((ss.substring(1, 3)));
//                                    cal.setText((ss.substring(4,4)));
//                                    if(ss.substring(8,9).equals("N")) {
//                                        spo.setText((ss.substring(8, 4)));
//                                        hb.setText((ss.substring(12, 4)));
//                                    }
//                                }
                            }
                        });

                    } catch (IOException e1)
                    { e1.printStackTrace();}

                }
            }

        });

        workerThread.start();
    }

    public class MyTask extends AsyncTask<Void,Void,Boolean>{

        MyTask(){

        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr,SERVERPORT);
                wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                rea = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                while(!rea.ready()){
                    Log.d("TAG","notReady");
                }
                if(rea.ready()){
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Toast.makeText(MainActivity.this,"connect", LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"connection Error", LENGTH_SHORT).show();

            }
            super.onPostExecute(aBoolean);
        }
    }


}
