package com.example.timelineprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventHelper extends SQLiteOpenHelper{

    private static final String Database_filename="events.db";
    private static final int Database_version=2;

    public EventHelper(Context context) {
        super(context, Database_filename, null, Database_version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table people (name text, age integer, height real);
        // For the name, we could have also said UNIQUE instead of NOT NULL 
        String sql = "CREATE TABLE " + Constants.EVENT_TABLE + " (" + 
                        Constants._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        Constants.NAME + " text NOT NULL, " +
                        Constants.MONTH  + " integer, "+
                        Constants.DAY  + " integer, "+
                        Constants.YEAR + " integer, " +
                        Constants.MILLISEC + " real); " ;
        db.execSQL(sql);        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + Constants.EVENT_TABLE);
        onCreate(db);        
    }

}
