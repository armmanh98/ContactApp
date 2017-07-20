package com.example.user.contactsapp.DataBasa;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.contactsapp.Contact.Contact;
import com.example.user.contactsapp.Image.Image;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactsManager";


    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_IMAGES = "images";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";

    private static final String KEY_IMAGE_ID = "image_id";
    private static final String KEY_IMAGE_PATH = "image_path";
    private static final String KEY_IMAGE_DESCRIPTION = "image_description";
    private static final String KEY_IMAGE_OWNER_ID = "image_owner_id";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," +   KEY_PH_NO + " TEXT," + KEY_AGE + " TEXT,"
                + KEY_GENDER + " TEXT" + ")";
        String CREATE_IMAGES_TABLE = "CREATE TABLE " + TABLE_IMAGES + "(" +
                KEY_IMAGE_ID + " INTEGER PRIMARY KEY," + KEY_IMAGE_PATH + " TEXT," +   KEY_IMAGE_DESCRIPTION + " TEXT,"
                + KEY_IMAGE_OWNER_ID + " INTEGER" + ")";
        db.execSQL(CREATE_IMAGES_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);

        onCreate(db);
    }



    public long addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getNumber());
        values.put(KEY_AGE, contact.getAge());
        values.put(KEY_GENDER, contact.getGender());

        long a = db.insert(TABLE_CONTACTS, null, values);
        db.close();
        return  a;
    }

    public void addImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values1 = new ContentValues();
        values1.put(KEY_IMAGE_PATH, image.getPath());
        values1.put(KEY_IMAGE_DESCRIPTION, image.getDescription());
        values1.put(KEY_IMAGE_OWNER_ID, image.getOwnerId());

        db.insert(TABLE_IMAGES, null, values1);
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

    public Image getImage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_IMAGES, new String[] { KEY_IMAGE_ID,
                        KEY_IMAGE_PATH, KEY_IMAGE_DESCRIPTION, KEY_IMAGE_OWNER_ID }, KEY_IMAGE_OWNER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Image image = new Image(
                cursor.getInt(0),cursor.getString(1), cursor.getString(2) , cursor.getInt(3));
        return image;
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

    public List<Image> getAllImages(int ownerId) {
        List<Image> imageList = new ArrayList<Image>();
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                if(Integer.parseInt(cursor.getString(3))==ownerId) {
                    Image image = new Image();
                    image.setId(Integer.parseInt(cursor.getString(0)));
                    image.setPath(cursor.getString(1));
                    image.setDescription(cursor.getString(2));
                    image.setOwnerId(Integer.parseInt(cursor.getString(3)));
                    imageList.add(image);
                }
            } while (cursor.moveToNext());
        }

        return imageList;
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
    public void deleteImage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_IMAGES, KEY_IMAGE_ID + " = ?",
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