package muzammil.muhammad.trellocardlist;

import android.content.ClipData;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
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
public class Fragment2 extends Fragment {
    private int counter2 = 0;
    private View view2;
    private ArrayList<Item> arrayList2;
    public static LinearLayoutListView area2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view2 = inflater.inflate(R.layout.fragment2,container,false);

        final ListView listView2 = (ListView) view2.findViewById(R.id.list2);
//        TextView textView = (TextView) view2.findViewById(R.id.addcard2);
        area2 = (LinearLayoutListView) view2.findViewById(R.id.list2Area);

        initItems();
        CardAdapter cardAdapter = new CardAdapter(getActivity().getApplicationContext(), arrayList2);
        listView2.setAdapter(cardAdapter);
        listView2.setDividerHeight(0);

        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView2.getItemAtPosition(position);
                Toast.makeText(getActivity(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }
        });

        area2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                String area;

                if (v == area2) {
                    area = "area1";
                } else if (v == Fragment2.area2) {
                    area = "area2";
                } else if (v == Fragment3.area3) {
                    area = "area3";
                } else {
                    area = "unknown";
                }

                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
//                        prompt.append("ACTION_DRAG_STARTED: " + area  + "\n");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:

//                        prompt.append("ACTION_DRAG_ENTERED: " + area  + "\n");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        float width = (listView2.getWidth()) / 3;
                        if (event.getX() > width) {
                            MainActivity.vpPager.setCurrentItem(3, true);

                        }
                        if (event.getX() < width) {
                            MainActivity.vpPager.setCurrentItem(1, true);

                        }

//                        MainActivity.vpPager.setCurrentItem(1);

//                        prompt.append("ACTION_DRAG_EXITED: " + area  + "\n");
                        break;
                    case DragEvent.ACTION_DROP:
//                        prompt.append("ACTION_DROP: " + area  + "\n");

                        PassObject passObj = (PassObject) event.getLocalState();
                        View view = passObj.view;
                        Item passedItem = passObj.item;
                        List<Item> srcList = passObj.srcList;
                        ListView oldParent = (ListView) view.getParent();
                        CardAdapter srcAdapter = (CardAdapter) (oldParent.getAdapter());

                        LinearLayoutListView newParent = (LinearLayoutListView) v;
                        CardAdapter destAdapter = (CardAdapter) (newParent.listView.getAdapter());
                        List<Item> destList = destAdapter.getList();

                        if (removeItemToList(srcList, passedItem)) {
                            addItemToList(destList, passedItem);
                        }

                        srcAdapter.notifyDataSetChanged();
                        destAdapter.notifyDataSetChanged();

                        //smooth scroll to bottom
                        newParent.listView.smoothScrollToPosition(destAdapter.getCount() - 1);

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:


//                        prompt.append("ACTION_DRAG_ENDED: " + area  + "\n");
                    default:
                        break;
                }
                return true;
            }
        });
        area2.setListView(listView2);


        return view2;
    }
    public List<Item> getList() {
        return arrayList2;
    }


    private void initItems() {
        arrayList2 = new ArrayList<Item>();


        TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);

        for (int i = 0; i < arrayDrawable.length(); i++) {
            Drawable d = arrayDrawable.getDrawable(i);
            String s = arrayText.getString(i);
            Item item = new Item(d, s);
            arrayList2.add(item);
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
