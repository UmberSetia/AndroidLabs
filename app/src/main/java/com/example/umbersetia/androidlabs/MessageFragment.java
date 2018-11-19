package com.example.umbersetia.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    ChatWindow parent = null;
    long messageID;
    String text;
    boolean isATablet = false;

    public MessageFragment(){}

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View screen = inflater.inflate(R.layout.activity_message_details, container, false);

        TextView message = screen.findViewById(R.id.message);
        TextView id = screen.findViewById(R.id.id_number);

        text = bundle.getString("Message");
        messageID = bundle.getLong("ID");

        id.setText(Long.toString(messageID));
        message.setText(text);

        return screen;
    }

    private void deleteButtonClick(View view){
        if (isATablet){
            parent.deleteMessage(messageID);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ftrans = fm.beginTransaction();
            ftrans.remove(this);
            ftrans.commit();
        } else {
            Intent intent = new Intent();
            intent.putExtra("ID",messageID);
            getActivity().setResult(20,intent);
            getActivity().finish();
        }
    }

    @Override
    public void onAttach(Activity context){
        super.onAttach(context);
        if (isATablet){
            parent = (ChatWindow) context;
        }
    }
}
