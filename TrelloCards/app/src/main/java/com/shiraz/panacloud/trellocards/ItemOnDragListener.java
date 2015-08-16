package com.shiraz.panacloud.trellocards;

import android.view.DragEvent;
import android.view.View;
import android.widget.ListView;

import java.util.List;

/**
 * Created by panacloud on 8/15/15.
 */
public class ItemOnDragListener implements View.OnDragListener {
    Item me;
    ItemOnDragListener(Item i){
        this.me = i;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
        case DragEvent.ACTION_DRAG_STARTED:
//        prompt.append("Item ACTION_DRAG_STARTED: " + "\n");
        break;
        case DragEvent.ACTION_DRAG_ENTERED:
//        prompt.append("Item ACTION_DRAG_ENTERED: " + "\n");
        v.setBackgroundColor(0x30000000);
        break;
//        case DragEvent.ACTION_DRAG_EXITED:
//        prompt.append("Item ACTION_DRAG_EXITED: " + "\n");
//        v.setBackgroundColor(resumeColor);
        case DragEvent.ACTION_DROP:
//        prompt.append("Item ACTION_DROP: " + "\n");

        PassObject passObj = (PassObject)event.getLocalState();
        View view = passObj.view;
        Item passedItem = passObj.item;
        List<Item> srcList = passObj.srcList;
        ListView oldParent = (ListView)view.getParent();
        CardAdapter srcAdapter = (CardAdapter)(oldParent.getAdapter());

        ListView newParent = (ListView)v.getParent();
        CardAdapter destAdapter = (CardAdapter)(newParent.getAdapter());
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

        break;
        case DragEvent.ACTION_DRAG_ENDED:

        default:
        break;
    }

    return true;
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
