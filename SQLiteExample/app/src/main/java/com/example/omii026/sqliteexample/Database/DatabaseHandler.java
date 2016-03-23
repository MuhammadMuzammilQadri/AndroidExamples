package com.example.omii026.sqliteexample.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.omii026.sqliteexample.ui.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omii026 on 3/23/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID    + " INTEGER PRIMARY KEY,"
            + KEY_NAME  + " TEXT,"
            + KEY_PH_NO + " TEXT " + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_CONTACTS);
      onCreate(sqLiteDatabase);
    }

//    addContacts
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,contact.getName());
        values.put(KEY_PH_NO,contact.getPhoneNumber());

        db.insert(TABLE_CONTACTS,null,values);
        db.close();
    }

//    rearContact
    public Contact getContact(int id){
    SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS,
                new String[]{ KEY_ID,KEY_NAME,KEY_PH_NO},
                KEY_ID + "=?",new String[]{String.valueOf(id)},
                null,null,null,null
                );

        if(cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));
        return contact;
    }

//    getAllContacts
    public List<Contact> getAllContacts(){

        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
            Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while(cursor.moveToNext());

        }
        return contactList;
    }

//    getContactCount
    public int getContactCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM "+TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(countQuery,null);
        db.close();

        return cursor.getCount();
    }

//    updateContact
    public int updateContact(Contact contact){
    SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,contact.getID());
        values.put(KEY_NAME,contact.getName());
        values.put(KEY_PH_NO,contact.getPhoneNumber());

        return  db.update(TABLE_CONTACTS,values,KEY_ID + "=?", new String[] {String.valueOf(contact.getID())});
    }

//    deleteContact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,KEY_ID + "=?",new String[]{String.valueOf(contact.getID())});
        db.close();
    }

//

























}
