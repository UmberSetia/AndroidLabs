package com.example.umbersetia.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle infoToPass = getIntent().getExtras();

        MessageFragment newFragment = new MessageFragment();
        newFragment.iAmTablet = false;
        newFragment.setArguments(infoToPass);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ftrans = fm.beginTransaction();
        ftrans.replace(R.id.fragment_location,newFragment);
        ftrans.addToBackStack("this_doesnt_matter");
        ftrans.commit();




    }

}
