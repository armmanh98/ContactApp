package com.example.user.contactsapp.DataBasa;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.contactsapp.Contact.Contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactsManager";

    private static final String TABLE_CONTACTS = "contacts";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," +   KEY_PH_NO + " TEXT," + KEY_AGE + " TEXT,"
                + KEY_GENDER + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }



    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getNumber());
        values.put(KEY_AGE, contact.getAge());
        values.put(KEY_GENDER, contact.getGender());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_AGE, KEY_GENDER }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(
                cursor.getInt(0),cursor.getString(1), cursor.getString(2) , cursor.getString(3), cursor.getString(4));
        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setNumber(cursor.getString(2));
                contact.setAge(cursor.getString(3));
                contact.setGender(cursor.getString(4));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getNumber());
        values.put(KEY_AGE, contact.getAge());
        values.put(KEY_GENDER, contact.getGender());

        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }


    public void deleteContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }



    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();


        return cursor.getCount();
    }

}