package com.example.e_tecklaptop.testproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.e_tecklaptop.testproject.model.AddsItem;

import java.util.ArrayList;

/**
 * Created by E-Teck Laptop on 7/27/2017.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ListingDB.db";
    private static final String TABLE_LISTING = "ListingItems";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_ADDRESS = "itemaddress";
    public static final String COLUMN_ITEM_DESCRIPTION = "description";
    public static final String COLUMN_ITEM_IMAGE = "image";
    public static final String COLUMN_ITEM_PRICE = "price";
    public static final String COLUMN_ITEM_BATHS = "baths";
    public static final String COLUMN_ITEM_BEDS = "beds";
    public static final String COLUMN_ITEM_AREA = "area";
    public static final String COLUMN_ITEM_LATITUDE = "latitude";
    public static final String COLUMN_ITEM_LONGITUDE = "longitude";
    public static final String COLUMN_ITEM_STYLE = "style";
    public static final String COLUMN_ITEM_PROPERTY_TYPE = "propertytype";
    public static final String COLUMN_ITEM_COMMUNITY = "community";
    public static final String COLUMN_ITEM_COUNTRY = "country";
    public static final String COLUMN_ITEM_MLSID = "mlsid";
    public static final String COLUMN_ITEM_LOTSIZE = "lotsize";
    public static final String COLUMN_ITEM_YEARBUILT = "yearbuilt";
    public static final String COLUMN_ITEM_ALL_IMAGES = "allimages";




    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    // create table query ( coulum with its charecteristic) : CREATE TABLE tablename(id INTEGER PRIMARY KEY, productname TEXT, quantity INTEGER )  {Note: don't add Qoma(,) in query}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_LISTING + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ITEM_ADDRESS
                + " TEXT, " + COLUMN_ITEM_DESCRIPTION + " TEXT, " + COLUMN_ITEM_IMAGE + " TEXT ," +  COLUMN_ITEM_PRICE + " TEXT, " +
                COLUMN_ITEM_BATHS + " TEXT ," + COLUMN_ITEM_BEDS + " TEXT ," +  COLUMN_ITEM_AREA + " TEXT ," + COLUMN_ITEM_LATITUDE + " TEXT ," +
                COLUMN_ITEM_LONGITUDE + " TEXT ," +  COLUMN_ITEM_STYLE + " TEXT ," +  COLUMN_ITEM_PROPERTY_TYPE + " TEXT ," +  COLUMN_ITEM_COMMUNITY + " TEXT ," +
                COLUMN_ITEM_COUNTRY + " TEXT ," +  COLUMN_ITEM_MLSID + " TEXT ," +  COLUMN_ITEM_LOTSIZE + " TEXT ," +  COLUMN_ITEM_YEARBUILT + " TEXT"+  ");";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTING);
        onCreate(db);
    }

    public void AddListingItems(AddsItem item) {

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ITEM_ADDRESS, item.getAddress());
            values.put(COLUMN_ITEM_DESCRIPTION, item.getAddress());
            values.put(COLUMN_ITEM_IMAGE, item.getImage());
            values.put(COLUMN_ITEM_PRICE, item.getPrice());
            values.put(COLUMN_ITEM_BATHS, item.getBaths());
            values.put(COLUMN_ITEM_BEDS, item.getBeds());
            values.put(COLUMN_ITEM_AREA, item.getArea());
            values.put(COLUMN_ITEM_LATITUDE, item.getLatitude());
            values.put(COLUMN_ITEM_LONGITUDE, item.getLogitude());
            values.put(COLUMN_ITEM_STYLE, item.getStyle());
            values.put(COLUMN_ITEM_PROPERTY_TYPE, item.getProperty_type());
            values.put(COLUMN_ITEM_COMMUNITY, "N/A");
            values.put(COLUMN_ITEM_COUNTRY, item.getCountry());
            values.put(COLUMN_ITEM_MLSID, item.getMlsID());
            values.put(COLUMN_ITEM_LOTSIZE, item.getLotSize());
            values.put(COLUMN_ITEM_YEARBUILT, item.getYearBuilt());

            SQLiteDatabase db = getWritableDatabase();


            db.insert(TABLE_LISTING, null, values);
            db.close();
        }catch (SQLiteException e){
            e.getMessage();
        }
    }

    public ArrayList<AddsItem> findItems() {
        //  String query = "Select * FROM " + TABLE_LISTING + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";
        String query = "Select * FROM " + TABLE_LISTING;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<AddsItem> ListingItems = new ArrayList<>();

        AddsItem  items ;// = new TransecItems();

        if (cursor.moveToFirst()) {
            do {
                //        cursor.moveToFirst();
                items = new AddsItem();
                items.setId(Integer.parseInt(cursor.getString(0)));
                items.setAddress(cursor.getString(1));
                items.setDescription(cursor.getString(2));
                items.setImage(cursor.getString(3));
                items.setPrice(cursor.getString(4));
                items.setBaths(cursor.getString(5));
                items.setBeds(cursor.getString(6));
                items.setArea(cursor.getString(7));
                items.setLatitude(cursor.getString(8));
                items.setLogitude(cursor.getString(9));
                items.setStyle(cursor.getString(10));
                items.setProperty_type(cursor.getString(11));
                items.setCommunity(cursor.getString(12));
                items.setCountry(cursor.getString(13));
                items.setMlsID(cursor.getString(14));
                items.setLotSize(cursor.getString(15));
                items.setYearBuilt(cursor.getString(16));
                ListingItems.add(items);
            }while (cursor.moveToNext());
        } else {
            items = null;
        }
       /* while(!cursor.isAfterLast())
        {

                items.setId(Integer.parseInt(cursor.getString(0)));
                items.setTitle(cursor.getString(1));
                items.setIcon(Integer.parseInt(cursor.getString(2)));
                items.setDescription(cursor.getString(3));
                items.setMoney(cursor.getString(4));

            cursor.moveToNext();
        }*/
        cursor.close();
        db.close();
        return ListingItems;
    }

     public void clearDatabase()
    {
        SQLiteDatabase sdb= this.getWritableDatabase();
        sdb.delete(TABLE_LISTING, null, null);

    }



}