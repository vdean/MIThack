package com.example.telpicprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public interface Constants extends BaseColumns{
    public static final String EVENT_TABLE="events";
    
    public static final String NAME="name";
    public static final String GAME_ID="gameid";
    public static final String ROUND="round";
    public static final String PLAYER_LIST="players";
    public static final String DRAWING="drawing";
    public static final String PHRASE="phrase";
    public static final String _ID = BaseColumns._ID;
    
    public static final String AUTHORITY="com.example.meg.telpic";
    public static final Uri EVENT_CONTENT_URI = Uri.parse("content://"+ 
                AUTHORITY + "/" + EVENT_TABLE);

}