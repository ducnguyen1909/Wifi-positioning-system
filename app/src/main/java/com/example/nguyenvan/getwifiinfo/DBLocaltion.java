package com.example.nguyenvan.getwifiinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nguyenvan on 5/3/2017.
 */

public class DBLocaltion extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "locationDB";
    // Contacts table name
    private static final String TABLE_LOCAL = "Local";
    // Shops Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_DISTANCE1 = "distance1";
    private static final String KEY_DISTANCE2 = "distance2";
    private static final String KEY_DISTANCE3 = "distance3";
    private static final String KEY_DISTANCE4 = "distance4";

    private ArrayList<Building> lstBuild;
    private Building building;
    public DBLocaltion(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOCAL + "(" + KEY_NAME +  " TEXT,"
        + KEY_DISTANCE1 + " DOUBLE," + KEY_DISTANCE2 + " DOUBLE," + KEY_DISTANCE3 + " DOUBLE," + KEY_DISTANCE4 + " DOUBLE" +")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCAL);
        // Creating tables again
        onCreate(db);
    }

    public void addLocation(Building building){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,building.getNameBuilding());
        values.put(KEY_DISTANCE1,building.getDistance1());
        values.put(KEY_DISTANCE2,building.getDistance2());
        values.put(KEY_DISTANCE3,building.getDistance3());
        values.put(KEY_DISTANCE4,building.getDistance4());


// Inserting Row
        db.insert(TABLE_LOCAL, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<Building> getBuilding(){
        lstBuild = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Local ", null);

        if (cursor.moveToFirst()) {
            do {
                building = new Building(cursor.getString(0),Double.parseDouble(cursor.getString(1)),Double.parseDouble(cursor.getString(2)),Double.parseDouble(cursor.getString(3)),Double.parseDouble(cursor.getString(4)));
                lstBuild.add(building);
            } while (cursor.moveToNext());
        }

        return lstBuild;
    }
    public void deleteDBLocation(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_LOCAL);
    }
}
