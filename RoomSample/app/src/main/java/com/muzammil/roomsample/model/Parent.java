package com.muzammil.roomsample.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * Created by Muzammil on 01-Apr-18.
 */

@Entity(tableName = "parent")
public class Parent {
    @ColumnInfo(name = "parent_name")
    private String name;
    
    public Parent(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Parent{" +
                "name='" + name + '\'' +
                '}';
    }
}
