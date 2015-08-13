package com.shiraz.panacloud.dragndrop;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    //items stored in ListView
    public class Item {
        Drawable ItemDrawable;
        String ItemString;
        Item(Drawable drawable, String t){
            ItemDrawable = drawable;
            ItemString = t;
        }
    }

    //objects passed in Drag and Drop operation
    class PassObject{
        View view;
        Item item;
        List<Item> srcList;

        PassObject(View v, Item i, List<Item> s){
            view = v;
            item = i;
            srcList = s;
        }
    }

    static class ViewHolder {
        ImageView icon;
        TextView text;
    }

    public class ItemsListAdapter extends BaseAdapter {

        private Context context;
        private List<Item> list;

        ItemsListAdapter(Context c, List<Item> l){
            context = c;
            list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // reuse views
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.row, null);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.icon = (ImageView) rowView.findViewById(R.id.rowImageView);
                viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) rowView.getTag();
            holder.icon.setImageDrawable(list.get(position).ItemDrawable);
            holder.text.setText(list.get(position).ItemString);

            rowView.setOnDragListener((View.OnDragListener) new ItemOnDragListener(list.get(position)));

            return rowView;
        }

        public List<Item> getList(){
            return list;
        }
    }

    List<Item> items1, items2, items3;
    ListView listView1, listView2, listView3;
    ItemsListAdapter myItemsListAdapter1, myItemsListAdapter2, myItemsListAdapter3;
    LinearLayoutListView area1, area2, area3;
    TextView prompt;

    //Used to resume original color in drop ended/exited
    int resumeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView1 = (ListView)findViewById(R.id.listview1);
        listView2 = (ListView)findViewById(R.id.listview2);
        listView3 = (ListView)findViewById(R.id.listview3);

        area1 = (LinearLayoutListView)findViewById(R.id.pane1);
        area2 = (LinearLayoutListView)findViewById(R.id.pane2);
        area3 = (LinearLayoutListView)findViewById(R.id.pane3);
        area1.setOnDragListener(myOnDragListener);
        area2.setOnDragListener(myOnDragListener);
        area3.setOnDragListener(myOnDragListener);
        area1.setListView(listView1);
        area2.setListView(listView2);
        area3.setListView(listView3);

        initItems();
        myItemsListAdapter1 = new ItemsListAdapter(this, items1);
        myItemsListAdapter2 = new ItemsListAdapter(this, items2);
        myItemsListAdapter3 = new ItemsListAdapter(this, items3);
        listView1.setAdapter(myItemsListAdapter1);
        listView2.setAdapter(myItemsListAdapter2);
        listView3.setAdapter(myItemsListAdapter3);

  /*
  //Auto scroll to end of ListView
  listView1.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
  listView2.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
  listView3.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
  */

        listView1.setOnItemClickListener(listOnItemClickListener);
        listView2.setOnItemClickListener(listOnItemClickListener);
        listView3.setOnItemClickListener(listOnItemClickListener);

        listView1.setOnItemLongClickListener(myOnItemLongClickListener);
        listView2.setOnItemLongClickListener(myOnItemLongClickListener);
        listView3.setOnItemLongClickListener(myOnItemLongClickListener);

        prompt = (TextView) findViewById(R.id.prompt);
        // make TextView scrollable
        prompt.setMovementMethod(new ScrollingMovementMethod());
        //clear prompt area if LongClick
        prompt.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                prompt.setText("");
                return true;
            }});

        resumeColor  = getResources().getColor(android.R.color.background_light);

    }

    AdapterView.OnItemLongClickListener myOnItemLongClickListener = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            Item selectedItem = (Item)(parent.getItemAtPosition(position));

            ItemsListAdapter associatedAdapter = (ItemsListAdapter)(parent.getAdapter());
            List<Item> associatedList = associatedAdapter.getList();

            PassObject passObj = new PassObject(view, selectedItem, associatedList);

            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, passObj, 0);

            return true;
        }

    };

    View.OnDragListener myOnDragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            String area;
            if(v == area1){
                area = "area1";
            }else if(v == area2){
                area = "area2";
            }else if(v == area3){
                area = "area3";
            }else{
                area = "unknown";
            }

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    prompt.append("ACTION_DRAG_STARTED: " + area  + "\n");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    prompt.append("ACTION_DRAG_ENTERED: " + area  + "\n");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    prompt.append("ACTION_DRAG_EXITED: " + area  + "\n");
                    break;
                case DragEvent.ACTION_DROP:
                    prompt.append("ACTION_DROP: " + area  + "\n");

                    PassObject passObj = (PassObject)event.getLocalState();
                    View view = passObj.view;
                    Item passedItem = passObj.item;
                    List<Item> srcList = passObj.srcList;
                    ListView oldParent = (ListView)view.getParent();
                    ItemsListAdapter srcAdapter = (ItemsListAdapter)(oldParent.getAdapter());

                    LinearLayoutListView newParent = (LinearLayoutListView)v;
                    ItemsListAdapter destAdapter = (ItemsListAdapter)(newParent.listView.getAdapter());
                    List<Item> destList = destAdapter.getList();

                    if(removeItemToList(srcList, passedItem)){
                        addItemToList(destList, passedItem);
                    }

                    srcAdapter.notifyDataSetChanged();
                    destAdapter.notifyDataSetChanged();

                    //smooth scroll to bottom
                    newParent.listView.smoothScrollToPosition(destAdapter.getCount()-1);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    prompt.append("ACTION_DRAG_ENDED: " + area  + "\n");
                default:
                    break;
            }

            return true;
        }

    };

    class ItemOnDragListener implements View.OnDragListener {

        Item  me;

        ItemOnDragListener(Item i){
            me = i;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    prompt.append("Item ACTION_DRAG_STARTED: " + "\n");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    prompt.append("Item ACTION_DRAG_ENTERED: " + "\n");
                    v.setBackgroundColor(0x30000000);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    prompt.append("Item ACTION_DRAG_EXITED: " + "\n");
                    v.setBackgroundColor(resumeColor);
                    break;
                case DragEvent.ACTION_DROP:
                    prompt.append("Item ACTION_DROP: " + "\n");

                    PassObject passObj = (PassObject)event.getLocalState();
                    View view = passObj.view;
                    Item passedItem = passObj.item;
                    List<Item> srcList = passObj.srcList;
                    ListView oldParent = (ListView)view.getParent();
                    ItemsListAdapter srcAdapter = (ItemsListAdapter)(oldParent.getAdapter());

                    ListView newParent = (ListView)v.getParent();
                    ItemsListAdapter destAdapter = (ItemsListAdapter)(newParent.getAdapter());
                    List<Item> destList = destAdapter.getList();

                    int removeLocation = srcList.indexOf(passedItem);
                    int insertLocation = destList.indexOf(me);
    /*
     * If drag and drop on the same list, same position,
     * ignore
     */
                    if(srcList != destList || removeLocation != insertLocation){
                        if(removeItemToList(srcList, passedItem)){
                            destList.add(insertLocation, passedItem);
                        }

                        srcAdapter.notifyDataSetChanged();
                        destAdapter.notifyDataSetChanged();
                    }

                    v.setBackgroundColor(resumeColor);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    prompt.append("Item ACTION_DRAG_ENDED: "  + "\n");
                    v.setBackgroundColor(resumeColor);
                default:
                    break;
            }

            return true;
        }

    }

    AdapterView.OnItemClickListener listOnItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(MainActivity.this,((Item)(parent.getItemAtPosition(position))).ItemString,
                    Toast.LENGTH_SHORT).show();
        }

    };

    private void initItems(){
        items1 = new ArrayList<Item>();
        items2 = new ArrayList<Item>();
        items3 = new ArrayList<Item>();

        TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);

        for(int i=0; i<arrayDrawable.length(); i++){
            Drawable d = arrayDrawable.getDrawable(i);
            String s = arrayText.getString(i);
            Item item = new Item(d, s);
            items1.add(item);
        }

        arrayDrawable.recycle();
        arrayText.recycle();
    }

    private boolean removeItemToList(List<Item> l, Item it){
        boolean result = l.remove(it);
        return result;
    }

    private boolean addItemToList(List<Item> l, Item it){
        boolean result = l.add(it);
        return result;
    }

}

