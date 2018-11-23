package com.example.umbersetia.androidlabs;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MessageFragment extends Fragment {
    protected static final String ACTIVITY_NAME = "MessageFragment";

    ChatWindow parent;
    Boolean iAmTablet=true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle infoToPass = getArguments();
        String passedMessage = infoToPass.getString("MESSAGES");
        final long idPassed = infoToPass.getLong("ID");
        View screen = inflater.inflate(R.layout.fragment_helper_layout, container, false);
        TextView tv = screen.findViewById(R.id.fragMsg);
        TextView tv2 = screen.findViewById(R.id.id);
        tv.setText("Message is: " + passedMessage);
        tv2.setText("Id is: " + idPassed);
        Button btn = screen.findViewById(R.id.fragbtn);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {

                                       if (iAmTablet) {
                                           Log.i(ACTIVITY_NAME, "we're in boss:");
                                           parent.deleteMessage(idPassed);
                                           MessageFragment newFragment = new MessageFragment();
                                           FragmentManager fm = getFragmentManager();
                                           android.app.FragmentTransaction ftrans = fm.beginTransaction();
                                           ftrans.remove(newFragment);
                                           ftrans.addToBackStack("this_doesnt_matter");
                                           ftrans.commit();


                                       } else {
                                           Intent resultIntent = new Intent();
                                           resultIntent.putExtra("id", idPassed);
                                           getActivity().setResult(67, resultIntent);
                                           getActivity().finish();

                                       }
                                   }
                               }


        );
        return screen;
    }

    public void onAttach(Activity context) {
        super.onAttach(context);
        if (iAmTablet) {
            parent = (ChatWindow) context;
        }
    }
}
