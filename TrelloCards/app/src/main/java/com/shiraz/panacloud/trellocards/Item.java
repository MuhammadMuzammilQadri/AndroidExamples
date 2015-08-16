package com.shiraz.panacloud.trellocards;

import android.graphics.drawable.Drawable;

/**
 * Created by panacloud on 8/12/15.
 */
public class Item {
    Drawable ItemDrawable;
    String ItemString;
    Item(Drawable drawable, String t){
        ItemDrawable = drawable;
        ItemString = t;
    }
}
