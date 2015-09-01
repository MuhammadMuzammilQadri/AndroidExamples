package muzammil.muhammad.gradientlist;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String data[]={"01","02","03","04","05","06","07","08","09",
        "10","11","12","13","14","15","16","17","18","19","20","21"};
        ListView listView= (ListView) findViewById(R.id.list);
        listView.setAdapter(new MyAdapter(this,R.layout.singlerow,R.id.mytext,data));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("MuzammilQadri", "in setOnItemClickListener()");
                TextView tempTextView= (TextView) view.findViewById(R.id.mytext);
                Toast.makeText(MainActivity.this, tempTextView.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    class MyAdapter extends ArrayAdapter<String>{


        private final Context context;
        private final int resource;
        private final int textViewID;
        private final String[] data;
        private LayoutInflater inflater;

        public MyAdapter(Context context, int resource, int textViewID, String[] data) {
            super(context, resource, data);
            this.context = context;
            this.resource = resource;
            this.textViewID = textViewID;
            this.data = data;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
                convertView = inflater.inflate(R.layout.singlerow,parent,false);

            if((position+1)%2 == 0 ) {
                convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            else
                convertView.setBackgroundColor(Color.TRANSPARENT);


            ((TextView)convertView.findViewById(R.id.mytext)).setText(data[position]);

            return convertView;
        }
    }


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
