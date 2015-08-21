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
public class Fragment1 extends Fragment {
    private int counter1 = 0;
    private View view1;
    private ArrayList<Item> arrayList1 = new ArrayList<>();
    public static LinearLayoutListView area1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view1 = inflater.inflate(R.layout.fragment1,container,false);

        final ListView listView1 = (ListView) view1.findViewById(R.id.list1);
//        TextView textView = (TextView) view1.findViewById(R.id.addcard1);
        area1 = (LinearLayoutListView) view1.findViewById(R.id.list1Area);

        initItems();
        CardAdapter cardAdapter = new CardAdapter(getActivity().getApplicationContext(), arrayList1);
        listView1.setAdapter(cardAdapter);
        listView1.setDividerHeight(0);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView1.getItemAtPosition(position);
                Toast.makeText(getActivity(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }
        });

        area1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                String area;

                if (v == area1) {
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
                        float width = (listView1.getWidth()) / 3;
                        if (event.getX() > width) {
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
        area1.setListView(listView1);

        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                arrayList1.add(String.valueOf(counter1++));
//                setListViewHeightBasedOnChildren(listView1);
//                cardAdapter.notifyDataSetChanged();
//                listView1.smoothScrollToPosition(cardAdapter.getCount());    //scrolling list to the bottom
//                /*
//                can also used
//                android:transcriptMode="alwaysScroll"
//                as ListView's xml attributes
//                */
//
//            }
//        });

//        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                arrayList1.remove(position);
//                setListViewHeightBasedOnChildren(listView1);
//                cardAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });

        return view1;

    }
    public List<Item> getList() {
        return arrayList1;
    }


    private void initItems() {
        arrayList1 = new ArrayList<Item>();


        TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);

        for (int i = 0; i < arrayDrawable.length(); i++) {
            Drawable d = arrayDrawable.getDrawable(i);
            String s = arrayText.getString(i);
            Item item = new Item(d, s);
            arrayList1.add(item);
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
