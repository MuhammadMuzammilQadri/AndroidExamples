package warsi.junaid.panacloud.toolbarpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class FirstFragment extends Fragment {

    public FirstFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container,false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.frist_fragment_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.fragment_firstitem:
                fragment_firstitem();
                return true;
            case R.id.fragment_seconditem:
                fragment_seconditem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void fragment_seconditem() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
//        Log.d("MuzammilQadri",""+getActivity().getSupportFragmentManager().getBackStackEntryCount());
    }

    private void fragment_firstitem() {
        Toast.makeText(getActivity(),"Fragment first item",Toast.LENGTH_SHORT).show();
    }


}
