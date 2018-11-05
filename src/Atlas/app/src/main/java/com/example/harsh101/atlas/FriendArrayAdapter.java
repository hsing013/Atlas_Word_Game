package com.example.harsh101.atlas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendArrayAdapter extends ArrayAdapter {
    public ArrayList<Friend> friendArrayList = null;
    public Context mContext = null;

    public FriendArrayAdapter(Context context, int id, ArrayList<Friend> list) {
        super(context, id, list);
        this.friendArrayList = list;
        this.mContext = context;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        System.out.println("Friend view was called");
        View currentView = convertView;
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        if (currentView == null) {
            currentView = inflater.inflate(R.layout.friend_element, parent, false);
        }

        TextView textView = currentView.findViewById(R.id.friendName);


        textView.setText(friendArrayList.get(pos).name);


        Button myButton = currentView.findViewById(R.id.gameInvite);

        myButton.setTag(friendArrayList.get(pos).name);


        return currentView;

    }
}
