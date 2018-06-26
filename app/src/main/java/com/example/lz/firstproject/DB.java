package com.example.lz.firstproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper{

    private final static int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "events.db";
    public static final String TABLE_PRODUCTS = "events";
    public static final String TASK = "task";
    public static final String IMPORTANCE = "importance";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String COL_ID = "id";


    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "("+
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASK + " TEXT, " +
                IMPORTANCE + " TEXT, " +
                DESCRIPTION+ " TEXT, " +
                DATE + " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public boolean addItem(listviewcode item){

        ContentValues values = new ContentValues();
        values.put(TASK, item.getTask());
        values.put(IMPORTANCE, item.getImportance());
        values.put(DESCRIPTION, item.getDescription());
        values.put(DATE, item.getDate());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();

        if (result == -1){
            return false;
        }
        else{
            return  true;
        }
    }

    public void deleteItem(listviewcode item){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PRODUCTS,  TASK + " = ? ", new String[] {item.getTask()});
    }

    public ArrayList<listviewcode> printEvents(String date){
        ArrayList<listviewcode> eventsOnDay =new ArrayList<listviewcode>();
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM " + TABLE_PRODUCTS + " WHERE " +  DATE + " = ? ", new String[] {date});

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            listviewcode item = new listviewcode();
            item.setTask(c.getString(c.getColumnIndex(DB.TASK)));
            item.setImportance(c.getString(c.getColumnIndex(DB.IMPORTANCE)));
            item.setDescription(c.getString(c.getColumnIndex(DB.DESCRIPTION)));
            item.setDate(c.getString(c.getColumnIndex(DB.DATE)));

            eventsOnDay.add(item);
        }
        c.close();
        db.close();
        return eventsOnDay;
    }
}


