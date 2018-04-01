package com.muzammil.roomsample.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.muzammil.roomsample.model.Parent;

/**
 * Created by Muzammil on 01-Apr-18.
 */

public class ParentConverters {
    
    @TypeConverter
    public static String fromParent(Parent parent) {
        Gson gson = new Gson();
        return gson.toJson(parent);
    }
    
    @TypeConverter
    public static Parent toParent(String parent) {
        Gson gson = new Gson();
        return gson.fromJson(parent, Parent.class);
    }
    
    
}
