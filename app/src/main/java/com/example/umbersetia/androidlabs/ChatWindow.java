package com.example.umbersetia.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private Button chatSend;
    private ListView chatArray;
    private EditText chat;
    private TextView row1;
    private TextView row2;
    ChatAdapter messageAdapter;

    Cursor results;

    ArrayList<ChatMessage> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "OnCreate");
        setContentView(R.layout.activity_chat_window);


        chatSend = (Button) findViewById(R.id.SendButton);
        chatArray = (ListView) findViewById(R.id.chatListView);
        chatArray.setClickable(true);
        chat = (EditText) findViewById(R.id.Chat);
        messageAdapter = new ChatAdapter(this);
        chatArray.setAdapter(messageAdapter);
        ChatDataBaseHelper dbOpener = new ChatDataBaseHelper(this);
        final SQLiteDatabase db = dbOpener.getWritableDatabase();
        results = db.query(ChatDataBaseHelper.TEXT_MESSAGE_TABLE, new String[]{ChatDataBaseHelper.KEY_ID, ChatDataBaseHelper.KEY_MESSAGES}, null, null, null, null, null);

        int numResults = results.getCount(); //how many rows
        int messagesColumnIndex = results.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGES);
        int idColumnIndex = results.getColumnIndex(ChatDataBaseHelper.KEY_ID);
        results.moveToFirst(); //read from first row

        while (!results.isAfterLast()) {
            long id = results.getLong(idColumnIndex);
            String msg = results.getString(messagesColumnIndex);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + msg);
            myList.add(new ChatMessage(id, msg));
            results.moveToNext();
        }

        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + results.getColumnCount());

        for (int i = 0; i < results.getColumnNames().length; i++) {
            Log.i(ACTIVITY_NAME, results.getColumnName(i));
        }

        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempText = chat.getText().toString();

                ContentValues newRow = new ContentValues();
                newRow.put(ChatDataBaseHelper.KEY_MESSAGES, tempText);
                //ready to insert into database:
                long id = db.insert(ChatDataBaseHelper.TEXT_MESSAGE_TABLE, "ReplacementValue", newRow);

                myList.add(new ChatMessage(id, tempText));
                //messageAdapter.notifyDataSetChanged();
                chat.setText("");

                messageAdapter.notifyDataSetChanged(); // data has changed
            }
        });


        chatArray.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //View frameLayout = findViewById(R.id.fragment_location);
                final boolean iAmTablet = true;
                Bundle infoToPass = new Bundle();
                infoToPass.clear();
                infoToPass.putString("MESSAGES", myList.get(position).message);
                infoToPass.putLong("ID", messageAdapter.getItemId(position));

                if (iAmTablet) {
                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                        Log.i(ACTIVITY_NAME, "tab in landscape");
                        MessageFragment newFragment = new MessageFragment();
                        newFragment.iAmTablet = true;
                        newFragment.setArguments(infoToPass);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ftrans = fm.beginTransaction();
                        ftrans.replace(R.id.FL, newFragment);
                        ftrans.addToBackStack("this_doesnt_matter");
                        ftrans.commit();
                    }else{
                        Log.i(ACTIVITY_NAME, "tab in portrait");
                        Intent nextPage = new Intent(ChatWindow.this, MessageDetails.class);
                        nextPage.putExtras(infoToPass);
                        startActivityForResult(nextPage, 67);

                    }
                } else {

                    Log.i(ACTIVITY_NAME, "this is a phone");
                    Intent nextPage = new Intent(ChatWindow.this, MessageDetails.class);
                    nextPage.putExtras(infoToPass);
                    startActivityForResult(nextPage, 67);
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 67) {
            Bundle extras = data.getExtras();
            deleteMessage(extras.getLong("id"));
            //messageAdapter.notifyDataSetChanged();
        }

    }


    public void deleteMessage(long id) {
        try {
            ChatDataBaseHelper dbOpener = new ChatDataBaseHelper(this);
            SQLiteDatabase db = dbOpener.getWritableDatabase();
            results = db.query(ChatDataBaseHelper.TEXT_MESSAGE_TABLE, new String[]{ChatDataBaseHelper.KEY_ID, ChatDataBaseHelper.KEY_MESSAGES}, null, null, null, null, null);

            int numResults = results.getCount(); //how many rows
            int messagesColumn = results.getColumnIndex(ChatDataBaseHelper.KEY_MESSAGES);
            int idColumn = results.getColumnIndex(ChatDataBaseHelper.KEY_ID);
            results.moveToFirst(); //read from first row

            while (!results.isAfterLast()) {
                Log.i(ACTIVITY_NAME, "ID is: " + results.getInt(idColumn));
                results.moveToNext();
            }


            Log.i(ACTIVITY_NAME, "delete message:" + String.valueOf(id));
            int numRowsDeleted = db.delete(ChatDataBaseHelper.TEXT_MESSAGE_TABLE, ChatDataBaseHelper.KEY_ID + " = " + id, null);
            Log.i(ACTIVITY_NAME, "#RowsDeleted:" + String.valueOf(numRowsDeleted));
            messageAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.i(ACTIVITY_NAME, "Exception thrown");
        }
    }

    private class ChatAdapter extends ArrayAdapter<ChatMessage> {

        public ChatAdapter(Context ctx) {

            super(ctx, 0);

        }

        public int getCount() {
            return myList.size();
        }

        public ChatMessage getItem(int position) {
            return myList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);

            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            }
            TextView tvMsg = (TextView) result.findViewById(R.id.message_text);
            tvMsg.setText(getItem(position).message);
            return result;


        }

        public long getItemId(int position) {
            return getItem(position).id;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "onDestroy()");
    }

}
