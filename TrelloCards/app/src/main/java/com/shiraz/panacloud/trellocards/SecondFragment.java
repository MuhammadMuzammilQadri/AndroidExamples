package com.shiraz.panacloud.trellocards;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panacloud on 8/11/15.
 */
public class SecondFragment extends Fragment {

    private String title;
    private int page;
    private ListView listView2;
    public static LinearLayoutListView area2;
    private CardAdapter cardAdapter;

    public SecondFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText("page2");

        listView2 = (ListView) view.findViewById(R.id.list2);
        area2 = (LinearLayoutListView) view.findViewById(R.id.list2Area);
        area2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                String area;

                if (v == FirstFragment.area1) {
                    area = "area1";
                } else if (v == SecondFragment.area2) {
                    area = "area2";
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
                        float  width = (listView2.getWidth())/3;
                        if (event.getX() < width) {
                            MainActivity.vpPager.setCurrentItem(0,true);
                        }
//                        MainActivity.vpPager.setCurrentItem(0);
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
        cardAdapter = new CardAdapter(getActivity().getApplicationContext(), new ArrayList<Item>());
        listView2.setAdapter(cardAdapter);
        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = (Item) (parent.getItemAtPosition(position));

                CardAdapter associatedAdapter = (CardAdapter) (parent.getAdapter());
                List<Item> associatedList = associatedAdapter.getList();

                PassObject passObj = new PassObject(view, selectedItem, associatedList);

                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, passObj, 0);

                return true;
            }
        });


        return view;
    }

    private boolean removeItemToList(List<Item> l, Item it) {
        boolean result = l.remove(it);
        return result;
    }

    private boolean addItemToList(List<Item> l, Item it) {
        boolean result = l.add(it);
        return result;
    }


//
//    }class MyDragListener implements View.OnDragListener {
////        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
////        Drawable normalShape = getResources().getDrawable(R.drawable.shape);
//
//        @Override
//        public boolean onDrag(View v, DragEvent event) {
//            int action = event.getAction();
//            switch (event.getAction()) {
//                case DragEvent.ACTION_DRAG_STARTED:
//                    // do nothing
//                    break;
//                case DragEvent.ACTION_DRAG_ENTERED:
////                    v.setBackgroundDrawable(enterShape);
//                    break;
//                case DragEvent.ACTION_DRAG_EXITED:
////                    v.setBackgroundDrawable(normalShape);
//                    break;
//                case DragEvent.ACTION_DROP:
//                    // Dropped, reassign View to ViewGroup
//                    View view = (View) event.getLocalState();
//                    ViewGroup owner = (ViewGroup) view.getParent();
//                    owner.removeView(view);
//                    LinearLayout container = (LinearLayout) v;
//                    container.addView(view);
//                    view.setVisibility(View.VISIBLE);
//                    break;
//                case DragEvent.ACTION_DRAG_ENDED:
////                    v.setBackgroundDrawable(normalShape);
//                default:
//                    break;
//            }
//            return true;
//        }
}
