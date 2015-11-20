package com.example.faiz.sqlliteexample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.faiz.sqlliteexample.database.ContactDbSchema.ContactTable;

/**
 * Created by faiz on 20/11/15.
 */
public class ContactBaseHelper extends SQLiteOpenHelper {
    private final static int VERSION = 1;

    public ContactBaseHelper(Context context) {
        super(context, ContactTable.NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                ContactTable.NAME + "(" +
                ContactTable.Cols.ID + " INTEGER PRIMARY KEY," +
                ContactTable.Cols.NAME + " TEXT," +
                ContactTable.Cols.PHONE_NUM + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ContactTable.NAME);

        // Create tables again
        onCreate(db);
    }
}
