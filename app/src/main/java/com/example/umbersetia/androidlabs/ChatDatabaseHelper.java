package com.example.umbersetia.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME;
    private static int VERSION_NUM;
    private static String KEY_ID, KEY_MESSAGE;

    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null,VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE" + db + "(KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,KEY_MESSAGE text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV){
        db.execSQL("DROP TABLE IF EXISTS" + db);
        onCreate(db);
    }
}
