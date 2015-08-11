package muzammil.muhammad.arrayadapterimplementation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ArrayList<String> descriptions;
    ArrayList<Integer> placeImages;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descriptions= new ArrayList<String>();
        placeImages= new ArrayList<Integer>();

        descriptions.add("I am description 1. The quick brown fox jumps over the lazy dog. Hence use me properly.");
        descriptions.add("I am description 2. The quick brown fox jumps over the lazy dog. Hence use me properly.");
        descriptions.add("I am description 3. The quick brown fox jumps over the lazy dog. Hence use me properly.");
        descriptions.add("I am description 4. The quick brown fox jumps over the lazy dog. Hence use me properly.");
        descriptions.add("I am description 5. The quick brown fox jumps over the lazy dog. Hence use me properly.");
        descriptions.add("I am description 6. The quick brown fox jumps over the lazy dog. Hence use me properly.");
        descriptions.add("I am description 7. The quick brown fox jumps over the lazy dog. Hence use me properly.");
        descriptions.add("I am description 8. The quick brown fox jumps over the lazy dog. Hence use me properly.");
        descriptions.add("I am description 9. The quick brown fox jumps over the lazy dog. Hence use me properly.");
        descriptions.add("I am description 10. The quick brown fox jumps over the lazy dog. Hence use me properly.");

        placeImages.add(R.drawable.a);
        placeImages.add(R.drawable.b);
        placeImages.add(R.drawable.c);
        placeImages.add(R.drawable.d);
        placeImages.add(R.drawable.e);
        placeImages.add(R.drawable.f);
        placeImages.add(R.drawable.g);
        placeImages.add(R.drawable.h);
        placeImages.add(R.drawable.i);
        placeImages.add(R.drawable.j);

        listview = (ListView) findViewById(R.id.listView);
        MyAdapter myAdapter= new MyAdapter(MainActivity.this, placeImages, descriptions);
        listview.setAdapter(myAdapter);
    }


    class MyAdapter extends ArrayAdapter<String>{

        Context context;
        ArrayList<Integer> placeImages;
        ArrayList<String> descriptions;
        MyAdapter(Context c, ArrayList<Integer> placeImages,ArrayList<String> descriptions){
            super(c, R.layout.bottompart, R.id.textView, descriptions);
            this.descriptions= descriptions;
            this.context=c;
            this.placeImages=placeImages;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view= inflater.inflate(R.layout.bottompart, parent, false);

            ((ImageView)(view.findViewById(R.id.imageView))).setImageResource(placeImages.get(position));
            ((TextView)(view.findViewById(R.id.textView))).setText(descriptions.get(position));

            return view;
        }
    }
}
