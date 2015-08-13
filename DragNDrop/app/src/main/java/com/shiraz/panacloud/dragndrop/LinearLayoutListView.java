package com.shiraz.panacloud.dragndrop;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by panacloud on 8/12/15.
 */
public class LinearLayoutListView  extends LinearLayout{

    ListView listView;

    public LinearLayoutListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public LinearLayoutListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public LinearLayoutListView(Context context, AttributeSet attrs,
                                int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public void setListView(ListView lv){
        listView = lv;
    }
}
