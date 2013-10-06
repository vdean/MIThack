package com.example.timelineprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public class EventContentProvider extends ContentProvider{

    EventHelper eh;
    private UriMatcher uriMatcher;
    static final int GOOD_PEOPLE=1;
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase mydb = eh.getWritableDatabase();
        int count =0;
        
        switch(uriMatcher.match(uri))
        {
        case GOOD_PEOPLE:
            count =mydb.delete(Constants.EVENT_TABLE, selection, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Delete has a bad URI");
                
        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase mydb = eh.getWritableDatabase();
        switch(uriMatcher.match(uri))
        {
        case GOOD_PEOPLE:
            mydb.insertOrThrow(Constants.EVENT_TABLE, null, values);
            break;
        default:
            throw new IllegalArgumentException("Insert has a bad URI");
                
        }
        
        return null;
    }

    @Override
    public boolean onCreate() {
        eh = new EventHelper(getContext());
        SQLiteDatabase mydb = eh.getWritableDatabase();
        if (mydb == null) return false;
        
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Constants.AUTHORITY, Constants.EVENT_TABLE, GOOD_PEOPLE);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = eh.getReadableDatabase();
        Cursor cursor=null;
        
        switch(uriMatcher.match(uri))
        {
        case GOOD_PEOPLE:
            cursor = db.query(Constants.EVENT_TABLE, projection, 
                    selection, selectionArgs, null, null, sortOrder);
            break;
        default:
            throw new IllegalArgumentException("Query has a bad URI");
                
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        SQLiteDatabase mydb = eh.getWritableDatabase();
        int count = 0;
        
        switch(uriMatcher.match(uri))
        {
        case GOOD_PEOPLE:
            count = mydb.update(Constants.EVENT_TABLE,  values, selection, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Update has a bad URI");
                
        }
        return count;
    }

}

