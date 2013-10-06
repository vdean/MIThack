package com.example.timeline;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class ClientDB {
    
    static Cursor getEvent(Context ctx)
    {
        Cursor cursor = null;
        try
        {
            ContentResolver cr = ctx.getContentResolver();
            cursor = cr.query(Constants.EVENT_CONTENT_URI, null, null, null,  Constants.NAME);
        }
        catch (SQLException e)
        {
            Log.d("Mine", "Query error: "+ e);
        }
        
        return cursor;
    }
    
    static int delete (Context ctx, String name)
    {
        int num = 0;

        try
        {
            ContentResolver cr = ctx.getContentResolver();
            String where_clause = Constants.NAME + "='" + name + "'";
            num = cr.delete(Constants.EVENT_CONTENT_URI, where_clause, null);
        }
        catch (SQLException e)
        {
            Log.d("Mine", "Delete error: "+ e);
        }
        return num;
    }
   
    static void insert(Context ctx, String name, int month, int day, int year, long milli)
    {
    	Log.d("Mine", "Trying to insert into db");
        try
        {
            ContentResolver cr = ctx.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(Constants.NAME, name);
            values.put(Constants.MONTH, month);
            values.put(Constants.DAY, day);
            values.put(Constants.YEAR, year);
            values.put(Constants.MILLISEC, milli);
            
            Log.d("Mine", "Put all values");
            cr.insert(Constants.EVENT_CONTENT_URI,  values);
        }
        catch (SQLException e)
        {
            Log.d("Mine", "Insert error: "+ e);
        }
        
    }

}
