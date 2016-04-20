package muzammil.muhammad.com.dialogpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDialog myDialog = new MyDialog(findViewById(R.id.tv1));
        myDialog.show(getSupportFragmentManager(),"muz");

    }



}
