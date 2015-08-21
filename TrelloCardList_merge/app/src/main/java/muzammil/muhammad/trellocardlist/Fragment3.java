package muzammil.muhammad.trellocardlist;

import android.content.ClipData;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panacloud on 8/20/15.
 */
public class Fragment3 extends Fragment {
    private int counter3 = 0;
    private View view3;
    private ArrayList<Item> arrayList3;
    public static LinearLayoutListView area3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view3 = inflater.inflate(R.layout.fragment3,container,false);
        final ListView listView3 = (ListView) view3.findViewById(R.id.list3);
        area3 = (LinearLayoutListView) view3.findViewById(R.id.list3Area);

        initItems();
        CardAdapter cardAdapter = new CardAdapter(getActivity().getApplicationContext(), arrayList3);
        listView3.setAdapter(cardAdapter);
        listView3.setDividerHeight(0);

        listView3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item selecteItem = (Item) (parent.getItemAtPosition(position));
                CardAdapter associatedAdapter = (CardAdapter) (parent.getAdapter());
                List<Item> associatedList = associatedAdapter.getList();

                PassObject passObj = new PassObject(view, selecteItem, associatedList);
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, passObj, 0);

                return true;
            }
        });

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView3.getItemAtPosition(position);
                Toast.makeText(getActivity(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }
        });

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                arrayList3.add(String.valueOf(counter3++));
//                setListViewHeightBasedOnChildren(listView3);
//                adapter.notifyDataSetChanged();
//                listView3.smoothScrollToPosition(adapter.getCount());    //scrolling list to the bottom
//                /*
//                can also used
//                android:transcriptMode="alwaysScroll"
//                as ListView's xml attributes
//                */
//
//            }
//        });

//        listView3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                arrayList3.remove(position);
//                setListViewHeightBasedOnChildren(listView3);
//                adapter.notifyDataSetChanged();
//                return false;
//            }
//        });
        return view3;

    }
    public List<Item> getList() {
        return arrayList3;
    }


    private void initItems() {
        arrayList3 = new ArrayList<Item>();


        TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);

        for (int i = 0; i < arrayDrawable.length(); i++) {
            Drawable d = arrayDrawable.getDrawable(i);
            String s = arrayText.getString(i);
            Item item = new Item(d, s);
            arrayList3.add(item);
        }

//        arrayDrawable.recycle();
//        arrayText.recycle();
    }


    private boolean removeItemToList(List<Item> l, Item it) {
        boolean result = l.remove(it);
        return result;
    }

    private boolean addItemToList(List<Item> l, Item it) {
        boolean result = l.add(it);
        return result;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
