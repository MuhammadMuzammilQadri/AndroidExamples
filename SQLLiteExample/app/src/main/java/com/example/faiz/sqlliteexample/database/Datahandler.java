package com.example.faiz.sqlliteexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.faiz.sqlliteexample.database.ContactDbSchema.ContactTable;
import com.example.faiz.sqlliteexample.database.ContactDbSchema.ContactTable.Cols;
import com.example.faiz.sqlliteexample.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faiz on 20/11/15.
 */
public class Datahandler {

    private static Datahandler OUR_INSTANCE;
    private Context _context;
    private ContactBaseHelper _contactBaseHelper;


    private Datahandler(Context context) {
        _context = context.getApplicationContext();
        _contactBaseHelper = new ContactBaseHelper(context);
        _contactBaseHelper.onUpgrade(_contactBaseHelper.getWritableDatabase(),0,0);
    }

    public static Datahandler getInstance(Context context){
        return OUR_INSTANCE!=null?OUR_INSTANCE:(OUR_INSTANCE= new Datahandler(context));
    }

    private ContentValues getContentValues(Contact contact){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cols.NAME,contact.getName());
        contentValues.put(Cols.PHONE_NUM,contact.getPhoneNumber());
        return contentValues;
    }

    // Adding new contact
    public void addContact(Contact contact) {
        SQLiteDatabase sqLiteDatabase = _contactBaseHelper.getWritableDatabase();
        sqLiteDatabase.insert(ContactTable.NAME,null,getContentValues(contact));
        sqLiteDatabase.close();
    }

    // Getting single contact
    public Contact getContact(int id) {
        SQLiteDatabase sqLiteDatabase = _contactBaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(ContactTable.NAME,//The table name to compile the query against.
                new String[]{Cols.ID, Cols.NAME, Cols.PHONE_NUM}, //A list of which columns to return. Passing null will * return all columns
                Cols.ID+ "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if (cursor != null && cursor.getCount()>0)
            cursor.moveToFirst();
        else return null;

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return contact;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();

        SQLiteDatabase db = _contactBaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + ContactTable.NAME, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + ContactTable.NAME;
        SQLiteDatabase db = _contactBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase sqLiteDatabase = _contactBaseHelper.getWritableDatabase();
        int x = sqLiteDatabase.update(ContactTable.NAME, getContentValues(contact), Cols.ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        sqLiteDatabase.close();
        return x;
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = _contactBaseHelper.getWritableDatabase();
        db.delete(ContactTable.NAME, ContactTable.Cols.ID+ " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

}
