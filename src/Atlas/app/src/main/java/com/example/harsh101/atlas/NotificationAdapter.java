package com.example.harsh101.atlas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;

public class NotificationAdapter extends ArrayAdapter {
    public ArrayList<Notification> myList = null;
    public Context aContext = null;

    public NotificationAdapter(Context context, int id, ArrayList<Notification> list) {
        super(context, id, list);
        this.myList = list;
        this.aContext = context;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        View currentView = convertView;
        LayoutInflater inflater = ((Activity) aContext).getLayoutInflater();
        if (currentView == null) {
            currentView = inflater.inflate(R.layout.notification_element, parent, false);
        }

        Notification n = myList.get(pos);

        TextView textView = currentView.findViewById(R.id.type);
        TextView textView1 = currentView.findViewById(R.id.from);
        Button button1 = currentView.findViewById(R.id.acceptButton);
        Button button2 = currentView.findViewById(R.id.denyButton);

        textView.setText(n.type);
        textView1.setText(n.from);
        button1.setTag(pos);
        button2.setTag(pos);

        return currentView;

    }


}
