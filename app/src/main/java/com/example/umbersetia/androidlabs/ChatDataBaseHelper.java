package com.example.umbersetia.androidlabs;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


public class ChatDataBaseHelper extends SQLiteOpenHelper {


    protected static final String ACTIVITY_NAME = "ChatDataBaseHelper";
    public static final String TEXT_MESSAGE_TABLE = "Text_Messages";
    public static final int VERSION_NUM = 4;
    public static final String KEY_ID = "ID";
    public static final String KEY_MESSAGES = "MESSAGES";

    public ChatDataBaseHelper(Context ctx) {
        super(ctx, "Messages", null, VERSION_NUM);


    }

   @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TEXT_MESSAGE_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGES + " TEXT NOT NULL);";

        db.execSQL(createTable);
        Log.i(ACTIVITY_NAME, "OnCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TEXT_MESSAGE_TABLE);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, Old version = " + oldVersion + " New version = " + newVersion);
    }


}

