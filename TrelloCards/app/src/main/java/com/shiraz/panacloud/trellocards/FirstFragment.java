package com.shiraz.panacloud.trellocards;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panacloud on 8/11/15.
 */
public class FirstFragment extends Fragment {
    private String title;
    private static final String msg = "msg";
    private int page;
    private ListView listView1;
    CardAdapter cardAdapter;
    private Button button;
    private ArrayList<Item> list = new ArrayList<>();
    LinearLayout.LayoutParams layoutParams;
    public static LinearLayoutListView area1;


    private Context context;

//     newInstance constructor for creating fragment with arguments
//    public static FirstFragment newInstance(int page, String title) {
//        FirstFragment fragmentFirst = new FirstFragment();
//        Bundle args = new Bundle();
//        args.putInt("someInt", page);
//        args.putString("someTitle", title);
//        fragmentFirst.setArguments(args);
//        return fragmentFirst;
//    }

    // Store instance variables based on arguments passed
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
//         LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_first, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText("page");
//        area1 = (LinearLayoutListView) view.findViewById(R.id.list1);
//        button = (Button) view.findViewById(R.id.button);
//        list.add("Card no");
//        list.add("Card no");
//        list.add("Card no");
//        list.add("Card no");
//        list.add("Card no");
//        list.add("Card no");
//        list.add("Card no");

        listView1 = (ListView) view.findViewById(R.id.list1);

//        listView1.setAdapter(cardAdapter);
        area1 = (LinearLayoutListView) view.findViewById(R.id.list1Area);
        area1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                String area;

                if (v == area1) {
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
                        float  width = (listView1.getWidth())/3;
                        if (event.getX() > width){
                            MainActivity.vpPager.setCurrentItem(1,true);

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

        initItems();
        CardAdapter cardAdapter = new CardAdapter(getActivity().getApplicationContext(), list);
        listView1.setAdapter(cardAdapter);
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

        return view;
    }


//     AdapterView.OnItemLongClickListener

    public List<Item> getList() {
        return list;
    }

    private void initItems() {
        list = new ArrayList<Item>();


        TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);

        for (int i = 0; i < arrayDrawable.length(); i++) {
            Drawable d = arrayDrawable.getDrawable(i);
            String s = arrayText.getString(i);
            Item item = new Item(d, s);
            list.add(item);
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


//    AdapterView.OnItemLongClickListener myLongClickListener = new AdapterView.OnItemLongClickListener() {
//        @Override
//        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//            Item item = (Item) (parent.getItemAtPosition(position));
//            CardAdapter mCardAdapter = (CardAdapter) (parent.getAdapter());
//            List<Item> associatedList = mCardAdapter.getList();
//            PassObject passObject = new PassObject(view,item,associatedList);
//
//            ClipData data = ClipData.newPlainText("", "");
//            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//            view.startDrag(data, shadowBuilder, passObject, 0);
//
//            return true;
//        }
//    };

//    private void initItems(){
//        items1 = new ArrayList<Item>();
//        items2 = new ArrayList<Item>();
//        items3 = new ArrayList<Item>();
//
//        TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
//        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);
//
//        for(int i=0; i<arrayDrawable.length(); i++){
//            Drawable d = arrayDrawable.getDrawable(i);
//            String s = arrayText.getString(i);
//            Item item = new Item(d, s);
//            items1.add(item);
//        }
//
//        arrayDrawable.recycle();
//        arrayText.recycle();
//    }

//    private boolean removeItemToList(List<Item> l, Item it){
//        boolean result = l.remove(it);
//        return result;
//    }
//
//    private boolean addItemToList(List<Item> l, Item it){
//        boolean result = l.add(it);
//        return result;
//    }

//    View.OnDragListener myOnDragListener = new View.OnDragListener() {
//        @Override
//        public boolean onDrag(View v, DragEvent event) {
//
//            String area;
//            if (v == area1) {
//                area = "area1";
//            } else if (v == SecondFragment.area2) {
//                area = "area2";
//
//            } else {
//                area = "unknown";
//            }
//
//            switch (event.getAction()) {
//                case DragEvent.ACTION_DRAG_STARTED:
////                    prompt.append("ACTION_DRAG_STARTED: " + area  + "\n");
//                    break;
//                case DragEvent.ACTION_DRAG_ENTERED:
////                    prompt.append("ACTION_DRAG_ENTERED: " + area  + "\n");
//                    break;
//                case DragEvent.ACTION_DRAG_EXITED:
////                    prompt.append("ACTION_DRAG_EXITED: " + area  + "\n");
//                    break;
//                case DragEvent.ACTION_DROP:
////                    prompt.append("ACTION_DROP: " + area  + "\n");
//
//                    PassObject passObj = (PassObject) event.getLocalState();
//                    View view = passObj.view;
//                    Item passedItem = passObj.item;
//                    List<Item> srcList = passObj.srcList;
//                    ListView oldParent = (ListView) view.getParent();
//                    CardAdapter srcAdapter = (CardAdapter) (oldParent.getAdapter());
//
//                    LinearLayoutListView newParent = (LinearLayoutListView) v;
//                    CardAdapter destAdapter = (CardAdapter) (newParent.listView.getAdapter());
//                    List<Item> destList = destAdapter.getList();
//
//                    if (removeItemToList(srcList, passedItem)) {
//                        addItemToList(destList, passedItem);
//                    }
//
//                    srcAdapter.notifyDataSetChanged();
//                    destAdapter.notifyDataSetChanged();
//
//                    //smooth scroll to bottom
//                    newParent.listView.smoothScrollToPosition(destAdapter.getCount() - 1);
//
//                    break;
//                case DragEvent.ACTION_DRAG_ENDED:
////                    prompt.append("ACTION_DRAG_ENDED: " + area  + "\n");
//                default:
//                    break;
//            }
//            return true;
//        }
//    };
}


//        button.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ClipData.Item item = new ClipData.Item((CharSequence) v
//                        .getTag());
//                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
//                ClipData dragData = new ClipData(v.getTag().toString(),
//                        mimeTypes, item);
//
//                // Instantiates the drag shadow builder.
//                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
//
//                // Starts the drag
//                v.startDrag(dragData, // the data to be dragged
//                        myShadow, // the drag shadow builder
//                        null, // no need to use local data
//                        0 // flags (not currently used, set to 0)
//                );
//                return true;
//            }
//        });

//        button.setOnDragListener(new View.OnDragListener() {
//            @SuppressLint("NewApi")
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                switch (event.getAction()) {
//                    case DragEvent.ACTION_DRAG_STARTED:
//                        layoutParams = (LinearLayout.LayoutParams) v
//                                .getLayoutParams();
//                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");
//                        break;
//                    case DragEvent.ACTION_DRAG_ENTERED:
//                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED");
//                        int x = (int) event.getX();
//                        int y = (int) event.getY();
//                        break;
//                    case DragEvent.ACTION_DRAG_EXITED:
//                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED");
//                        x = (int) event.getX();
//                        y = (int) event.getY();
//                        layoutParams.leftMargin = x;
//                        layoutParams.topMargin = y;
//                        v.setLayoutParams(layoutParams);
//                        break;
//                    case DragEvent.ACTION_DRAG_LOCATION:
//                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION");
//                        x = (int) event.getX();
//                        y = (int) event.getY();
//                        break;
//                    case DragEvent.ACTION_DRAG_ENDED:
//                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");
//
//                        break;
//                    case DragEvent.ACTION_DROP:
//                        Log.d(msg, "ACTION_DROP event");
//
//                        break;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });
//        Button add = (Button) view.findViewById(R.id.button);


//        cardAdapter = new CardAdapter(getActivity(),list);
//        listView.setAdapter(cardAdapter);

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
//                ClipData.Item item = new ClipData.Item((CharSequence) v
//                        .getTag());
//                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
//                ClipData dragData = new ClipData(v.getTag().toString(),
//                        mimeTypes, item);
//
//                // Instantiates the drag shadow builder.
//                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
//
//                // Starts the drag
//                v.startDrag(dragData, // the data to be dragged
//                        myShadow, // the drag shadow builder
//                        null, // no need to use local data
//                        0 // flags (not currently used, set to 0)
//                );
//                return true;
//            }
//        });
//
//        listView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ClipData.Item item = new ClipData.Item((CharSequence) v
//                        .getTag());
//                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
//                ClipData dragData = new ClipData(v.getTag().toString(),
//                        mimeTypes, item);
//
//                // Instantiates the drag shadow builder.
//                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
//
//                // Starts the drag
//                v.startDrag(dragData, // the data to be dragged
//                        myShadow, // the drag shadow builder
//                        null, // no need to use local data
//                        0 // flags (not currently used, set to 0)
//                );
//                return true;
//            }
//        });


//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
