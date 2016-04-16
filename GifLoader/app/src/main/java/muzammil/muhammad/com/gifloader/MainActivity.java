package muzammil.muhammad.com.gifloader;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    boolean flag;
    Button button;
    AlMeezanProgressDialog dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissDialog();
                    }
                }, 1000);
            }
        });
    }

    private void showDialog() {
        dialogFragment = AlMeezanProgressDialog.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "CustomDialog");
    }

    private void dismissDialog() {
        if (dialogFragment != null)
            dialogFragment.dismiss();
    }

}
