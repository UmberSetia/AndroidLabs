package com.example.umbersetia.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";

    protected Button button;
    protected Button startChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        button = findViewById(R.id.button);
        startChat = findViewById(R.id.button2);
    }

    protected void clickHandler(View view){
        Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
        startActivityForResult(intent, 50);

    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 50) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if (requestCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(this, messagePassed, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy");
    }

    protected void startChat(View view){
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
        Intent intent = new Intent(StartActivity.this, ChatWindow.class);
        startActivity(intent);
    }
}
