package com.example.e_tecklaptop.testproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.e_tecklaptop.testproject.model.Images;

import java.util.ArrayList;

/**
 * Created by E-Teck Laptop on 7/27/2017.
 */

public class PhotoDBHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "photoDB.db";
    private static final String TABLE_PRODUCTS = "photos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PROPERTY_ID = "propertyid";
    public static final String COLUMN_ITEM_IMAGE = "image";





    public PhotoDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    // create table query ( coulum with its charecteristic) : CREATE TABLE tablename(id INTEGER PRIMARY KEY, productname TEXT, quantity INTEGER )  {Note: don't add Qoma(,) in query}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PROPERTY_ID + " INTEGER, "  + COLUMN_ITEM_IMAGE + " TEXT" + ");";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addImage(Images item) {

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PROPERTY_ID, item.getPropertyID());
            values.put(COLUMN_ITEM_IMAGE, item.getImage());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_PRODUCTS, null, values);
            db.close();
        }catch (SQLiteException e){
            e.getMessage();
        }
    }

    public ArrayList<Images> findItems(int propertyID) {
          String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PROPERTY_ID + " =  \"" + propertyID + "\"";
     //   String query = "Select * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Images> imagesList = new ArrayList<>();

        Images  items ;// = new TransecItems();

        if (cursor.moveToFirst()) {
            do {
                //        cursor.moveToFirst();
                items = new Images();
                items.setID(Integer.parseInt(cursor.getString(0)));
                items.setPropertyID(Integer.parseInt(cursor.getString(1)));
                items.setImage(cursor.getString(2));
                imagesList.add(items);
            }while (cursor.moveToNext());
        } else {
            items = null;
        }

        cursor.close();
        db.close();
        return imagesList;
    }

    public void clearDatabase()
    {
        SQLiteDatabase sdb = getWritableDatabase();
        sdb.delete(TABLE_PRODUCTS, null, null);

    }


}
