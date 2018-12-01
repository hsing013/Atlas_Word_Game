package com.example.harsh101.atlas;

import android.widget.TextView;

public class Notification {
    public String type = null;
    public String from = null;
    public TextView textView = null;
    public String gameID = null;

    public Notification(String type, String from){
        this.type = type;
        this.from = from;
    }

}
