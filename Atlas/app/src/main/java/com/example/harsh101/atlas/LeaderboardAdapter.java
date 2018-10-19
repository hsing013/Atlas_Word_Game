package com.example.harsh101.atlas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;




public class LeaderboardAdapter extends ArrayAdapter {
    private ArrayList<LeaderboardUser> myList;
    private Context aContext;

    public LeaderboardAdapter(Context context, int id, ArrayList<LeaderboardUser> list) {
        super(context, id, list);
        this.myList = list;
        this.aContext = context;
    }

    public void updateList(ArrayList<LeaderboardUser> newList){
        myList = newList;

    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = ((Activity) aContext).getLayoutInflater();
        if(v == null) {
            v = inflater.inflate(R.layout.leaderboard_list, parent, false);
        }
        LeaderboardUser currentUser = myList.get(pos);

        TextView left = v.findViewById(R.id.textView1);
        TextView right = v.findViewById(R.id.textView2);

        left.setText(currentUser.getName());
        right.setText(String.valueOf(currentUser.getNumPoints()));

        return v;
    }
}
