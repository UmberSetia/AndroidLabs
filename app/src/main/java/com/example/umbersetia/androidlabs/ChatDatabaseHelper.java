package com.example.umbersetia.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "Messages.db";
    private final static int VERSION_NUM = 2;
    public final static String KEY_ID = "ID";
    public final static String KEY_MESSAGE = "Message";
    public final static String TABLE_NAME = "Messages";

    public ChatDatabaseHelper(Context ctx){
<<<<<<< HEAD
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
=======

        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        System.out.println("Here");
>>>>>>> 5da5b674b949aac7388ef486fe6394535ff33118
    }

    @Override
    public void onCreate(SQLiteDatabase db){
<<<<<<< HEAD
        //db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " String );");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, Message String);");
=======
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " String );");
>>>>>>> 5da5b674b949aac7388ef486fe6394535ff33118
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV){
<<<<<<< HEAD
=======
        System.out.println("Not here");
>>>>>>> 5da5b674b949aac7388ef486fe6394535ff33118
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldV + " newVersion=" + newV);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 5da5b674b949aac7388ef486fe6394535ff33118
