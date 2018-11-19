package com.example.umbersetia.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ListView listView;
    private EditText editText;
    public ArrayList<String> chatMessages = new ArrayList<>();
    private ChatAdapter messageAdapter;
    private TextView message;
    public SQLiteDatabase db;
    private ContentValues cValues;
    private ChatDatabaseHelper chatDatabaseHelper;
    public Cursor c;
    final int REQUEST_CODE = 10;

    public Boolean frameLayoutExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate");

        View frameLayout = findViewById(R.id.frameLayout);
        final boolean iAmTablet = (frameLayout != null);

        cValues = new ContentValues();

        chatDatabaseHelper = new ChatDatabaseHelper(this);
        db = chatDatabaseHelper.getWritableDatabase();

        loadListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Bundle infoToPass = new Bundle();
                infoToPass.putString("Message",message.getText().toString());
                infoToPass.putLong("ID", messageAdapter.getItemId(position));

                if (iAmTablet){
                    MessageFragment messageFragment = new MessageFragment();
                    messageFragment.isATablet = true;
                    android.app.FragmentManager fm = getFragmentManager();
                    android.app.FragmentTransaction ftrans = fm.beginTransaction();
                    ftrans.replace(R.id.chatView,messageFragment);
                    ftrans.addToBackStack("");
                    ftrans.commit();
                } else {
                    Intent intent = new Intent(ChatWindow.this,MessageDetails.class);
                    intent.putExtras(infoToPass);
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }
        });
    }

    private void loadListView(){
        c = db.rawQuery("SELECT Message,ID FROM Messages", null);

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

    public void deleteMessage(long id){
        db.delete(chatDatabaseHelper.TABLE_NAME,chatDatabaseHelper.KEY_ID + "=" + id,null);
        loadListView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_CODE && resultCode == 20){
            long id = data.getLongExtra("ID",0);
            this.deleteMessage(id);
        }
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
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));

            return result;
        }

        @Override
        public long getItemId(int position){
            c.moveToPosition(position);

            return c.getInt(c.getColumnIndex(ChatDatabaseHelper.KEY_ID));
        }

    }
}
