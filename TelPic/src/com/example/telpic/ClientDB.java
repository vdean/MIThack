package com.example.telpic;

import java.io.ByteArrayOutputStream;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
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
    
    static void insertDrawing(Context ctx, byte[] drawing, int game_id, int round, String name) {
    	try
        {
            ContentResolver cr = ctx.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(Constants.GAME_ID, game_id);
            values.put(Constants.ROUND, round);
            values.put(Constants.NAME, name);
            values.put(Constants.DRAWING, drawing);
            Log.d("Mine", "Put all values");
            cr.insert(Constants.EVENT_CONTENT_URI,  values);
        }
        catch (SQLException e)
        {
            Log.d("Mine", "Insert error: "+ e);
        }
    }
    
    static void insertPhrase(Context ctx, String phrase, int game_id, int round, String name) {
    	try
        {
            ContentResolver cr = ctx.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(Constants.GAME_ID, game_id);
            values.put(Constants.ROUND, round);
            values.put(Constants.NAME, name);
            values.put(Constants.PHRASE, phrase);
            Log.d("Mine", "Put all values");
            cr.insert(Constants.EVENT_CONTENT_URI,  values);
        }
        catch (SQLException e)
        {
            Log.d("Mine", "Insert error: "+ e);
        }
    }
    
//    static byte[] retrieveImage(Context ctx, int game_id, int round, String name) {
//    	try
//        {
//            ContentResolver cr = ctx.getContentResolver();
//            ContentValues values = new ContentValues();
//            values.put(Constants.GAME_ID, game_id);
//            values.put(Constants.ROUND, round);
//            values.put(Constants.NAME, name);
//            values.put(Constants.DRAWING, drawing);
//            Log.d("Mine", "Put all values");
//            cr.insert(Constants.EVENT_CONTENT_URI,  values);
//        }
//        catch (SQLException e)
//        {
//            Log.d("Mine", "Insert error: "+ e);
//        }
//    }
}