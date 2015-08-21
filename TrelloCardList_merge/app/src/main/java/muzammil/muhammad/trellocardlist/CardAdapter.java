package muzammil.muhammad.trellocardlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by panacloud on 8/11/15.
 */
public class CardAdapter extends BaseAdapter {

    public ArrayList<Item> list;
    LayoutInflater inflater;
    Context mContext;
   AbsListView.LayoutParams layoutParams;
    private static final String msg = "msg";


    View view;

    public CardAdapter(Context context, ArrayList<Item> list){
        this.mContext = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

             convertView = inflater.inflate(R.layout.singlerow,parent,false);

        }
        ((TextView) convertView.findViewById(R.id.text1)).setText(list.get(position).ItemString);

//        convertView.setOnDragListener((ItemOnDragListener) new ItemOnDragListener(list.get(position)));
        return convertView;
    }

    public ArrayList<Item> getList() {
        return list;
    }

//    class ItemOnDragListener implements View.OnDragListener {
//
//        Item  me;
//
//        ItemOnDragListener(Item i){
//            me = i;
//        }
//
//        @Override
//        public boolean onDrag(View v, DragEvent event) {
//            switch (event.getAction()) {
//                case DragEvent.ACTION_DRAG_STARTED:
////                    prompt.append("Item ACTION_DRAG_STARTED: " + "\n");
//                    break;
//                case DragEvent.ACTION_DRAG_ENTERED:
////                    prompt.append("Item ACTION_DRAG_ENTERED: " + "\n");
//                    v.setBackgroundColor(0x30000000);
//                    break;
//                case DragEvent.ACTION_DRAG_EXITED:
////                    prompt.append("Item ACTION_DRAG_EXITED: " + "\n");
////                    v.setBackgroundColor(resumeColor);
//                    break;
//                case DragEvent.ACTION_DROP:
////                    prompt.append("Item ACTION_DROP: " + "\n");
//
//                    PassObject passObj = (PassObject)event.getLocalState();
//                    View view = passObj.view;
//                    Item passedItem = passObj.item;
//                    List<Item> srcList = passObj.srcList;
//                    ListView oldParent = (ListView)view.getParent();
//                    ItemsListAdapter srcAdapter = (ItemsListAdapter)(oldParent.getAdapter());
//
//                    ListView newParent = (ListView)v.getParent();
//                    ItemsListAdapter destAdapter = (ItemsListAdapter)(newParent.getAdapter());
//                    List<Item> destList = destAdapter.getList();
//
//                    int removeLocation = srcList.indexOf(passedItem);
//                    int insertLocation = destList.indexOf(me);
//    /*
//     * If drag and drop on the same list, same position,
//     * ignore
//     */
//                    if(srcList != destList || removeLocation != insertLocation){
//                        if(removeItemToList(srcList, passedItem)){
//                            destList.add(insertLocation, passedItem);
//                        }
//
//                        srcAdapter.notifyDataSetChanged();
//                        destAdapter.notifyDataSetChanged();
//                    }
//
//                    v.setBackgroundColor(resumeColor);
//
//                    break;
//                case DragEvent.ACTION_DRAG_ENDED:
//                    prompt.append("Item ACTION_DRAG_ENDED: "  + "\n");
//                    v.setBackgroundColor(resumeColor);
//                default:
//                    break;
//            }
//
//            return true;
//        }

    }






//    class MyDragListener implements View.OnDragListener {
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
//    }
//
//    private final class MyTouchListener implements View.OnTouchListener {
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                ClipData data = ClipData.newPlainText("", "");
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//                view.startDrag(data, shadowBuilder, view, 0);
////                view.setVisibility(View.INVISIBLE);
//                return false;
//            } else {
//                return false;
//            }
//        }
//    }


//}
