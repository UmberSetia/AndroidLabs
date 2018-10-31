package com.example.umbersetia.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ListView listView;
    private EditText editText;
    public ArrayList<String> chatMessages = new ArrayList<String>();
    private ChatAdapter messageAdapter;
    private TextView message;
    public SQLiteDatabase db;
    private ContentValues cValues;
    private ChatDatabaseHelper chatDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate");

        cValues = new ContentValues();

        chatDatabaseHelper = new ChatDatabaseHelper(this);
        db = chatDatabaseHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT Message FROM Messages", null);

        int columnIndex = c.getColumnIndex("Message");

        c.moveToFirst();
        while (!c.isAfterLast()){
            Log.i(ACTIVITY_NAME, "SQL_MESSAGE: " + c.getString(c.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + c.getColumnCount());
            Log.i(ACTIVITY_NAME, "Column name = " + c.getColumnName(columnIndex));

            c.moveToNext();
        }

        listView = findViewById(R.id.chatView);
        editText = findViewById(R.id.editText);

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

    }

    protected void onSendClick(View view){
        Log.i(ACTIVITY_NAME, "Send was pressed");

            chatMessages.add(editText.getText().toString());

            cValues.put("Message", editText.getText().toString());
            db.insert(chatDatabaseHelper.TABLE_NAME,null,cValues);

            messageAdapter.notifyDataSetChanged();
            editText.setText("");

    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx){
            super(ctx,0);
        }


        public int getCount() {
            return chatMessages.size();
        }


        public String getItem(int position){
            Log.i(ACTIVITY_NAME, "In getItem");
            return chatMessages.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position%2 == 0){
                System.out.println("Here" + position);
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                System.out.println("There" + position);

                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));

            return result;
        }

        @Override
        public long getItemId(int position){
            return position;
        }
    }
}
