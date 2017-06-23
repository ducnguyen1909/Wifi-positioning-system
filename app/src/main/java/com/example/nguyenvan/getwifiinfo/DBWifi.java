package com.example.nguyenvan.getwifiinfo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nguyenvan on 5/8/2017.
 */

public class DBWifi extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "WifiDB";
    // Contacts table name
    private static final String TABLE_WIFI = "NameWifi";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private ArrayList<Wifi> lstWifi;
    private Wifi wifi;
    private SQLiteOpenHelper helper;

    public DBWifi(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WIFI + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME +  " TEXT"
                +")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFI);
        // Creating tables again
        onCreate(db);
    }

    public long addWifi(String wifiName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,wifiName);
// Inserting Row
        long id = db.insert(TABLE_WIFI, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public String getWifi(){
        String kq = "";
        lstWifi = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NameWifi ", null);

        if (cursor.moveToFirst()) {
            do {
                kq = cursor.getString(1);
            } while (cursor.moveToNext());
        }

        return kq;
    }

    public void deleteWifi(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_WIFI);
    }

}
