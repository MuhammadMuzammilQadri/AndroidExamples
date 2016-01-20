package muzammil.muhammad.viewpagerpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Muhammad Muzammil on 24-Nov-15.
 */
public class DemoFragment extends Fragment {

    public static DemoFragment newInstance(String text) {

        DemoFragment f = new DemoFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);



        f.setArguments(b);

        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_demo, container, false);

        TextView tv = (TextView) v.findViewById(R.id.demo_text_view);
        tv.setText(getArguments().getString("msg"));

        return v;    }
}
