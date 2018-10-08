package com.example.umbersetia.androidlabs;

import android.app.Activity;
import android.content.Context;
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
<<<<<<< HEAD

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ListView listView;
    private EditText editText;
    private ArrayList<String> chatMessages = new ArrayList<>();
    private ChatAdapter messageAdapter;
    private TextView message;
=======
public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ListView listView;
    protected EditText editText;
    protected Button sendButton;
    protected ArrayList<String> chatMessages;
    protected ChatAdapter messageAdapter;
>>>>>>> d9cfcf85f1fe6200ff07a54a090e0af3203522a3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
<<<<<<< HEAD
        Log.i(ACTIVITY_NAME, "In onCreate");

        listView = findViewById(R.id.chatView);
        editText = findViewById(R.id.editText);
=======

        listView.findViewById(R.id.chatView);
        editText.findViewById(R.id.editText);
        sendButton.findViewById(R.id.sendButton);
>>>>>>> d9cfcf85f1fe6200ff07a54a090e0af3203522a3

        Log.i(ACTIVITY_NAME, "In onCreate");
        chatMessages = new ArrayList<>();

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
<<<<<<< HEAD
=======

    }
>>>>>>> d9cfcf85f1fe6200ff07a54a090e0af3203522a3

    }

    protected void onSendClick(View view){
        Log.i(ACTIVITY_NAME, "Send was pressed");

            chatMessages.add(editText.getText().toString());

            messageAdapter.notifyDataSetChanged();

            editText.setText("");

    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx){
            super(ctx,0);
        }


        public int getCount() {

            return (chatMessages == null)?0 : chatMessages.size();
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
