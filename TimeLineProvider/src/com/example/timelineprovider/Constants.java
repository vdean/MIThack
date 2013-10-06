package com.example.timelineprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public interface Constants extends BaseColumns{
    public static final String EVENT_TABLE="events";
    
    public static final String NAME="name";
    public static final String MONTH="month";
    public static final String DAY="day";
    public static final String YEAR="year";
    public static final String MILLISEC="milli";
    public static final String _ID = BaseColumns._ID;
    
    public static final String AUTHORITY="com.example.meg.timeline";
    public static final Uri EVENT_CONTENT_URI = Uri.parse("content://"+ 
                AUTHORITY + "/" + EVENT_TABLE);

}