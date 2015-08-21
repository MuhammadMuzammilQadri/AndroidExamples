package muzammil.muhammad.trellocardlist;

import android.view.View;

import java.util.List;

/**
 * Created by panacloud on 8/12/15.
 */
public class PassObject {

    View view;
    Item item;
    List<Item> srcList;

    PassObject(View v, Item i, List<Item> s){
        view = v;
        item = i;
        srcList = s;
    }
}
