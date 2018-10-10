package com.example.harsh101.atlas;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class DataBase{
    private SQLiteDatabase db;

    public DataBase(Context context){
        db = context.openOrCreateDatabase("USERDATA", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS CONFIG (USERNAME TEXT, PASSWORD TEXT);"); //will expand to include other data

        db.execSQL("CREATE TABLE IF NOT EXISTS FREINDLIST (FRIEND TEXT);");

    }

    public ArrayList<String> getConfig(){ //will return userName and password if they exist
        Cursor c = db.rawQuery("SELECT * FROM CONFIG", null); //this selects everything from the CONFIG table

        c.moveToFirst(); //moves to the first row of data
        ArrayList<String> config = null;
        if (c.getCount() > 0){ //if there is data in the table
            config = new ArrayList<>(); //ArrayList are like vectors in C++
            config.add(c.getString(0));       //gets the USERNAME
            config.add(c.getString(1));       // gets the PASSWORD
        }
        c.close();
        return config;


    }

    public void updateConfig(String userName, String pass){
        Cursor c = db.rawQuery("SELECT * FROM CONFIG", null); //this selects everything from the CONFIG table

        c.moveToFirst();

        if (c.getCount() == 0){ //table is empty
            db.execSQL("INSERT INTO CONFIG (USERNAME, PASSWORD) VALUES ('" + userName + "', '" + pass + "');");
        }
        else{ //the table is not empty
            db.execSQL("UPDATE CONFIG SET USERNAME = '" + userName + "', PASSWORD = '" + pass + "';");
        }

        c.close();

    }

    public void addFriend(String name){
        db.execSQL("INSERT INTO FREINDLIST (NAME) VALUES('" + name + "');");
    }

    public ArrayList<String> getFriendList(){
        Cursor c = db.rawQuery("SELECT * FROM FRIENDLIST", null);

        ArrayList<String> friends = null;

        while(c.moveToNext()){ //iterates through all the rows
            if (friends == null){
                friends = new ArrayList<>();
            }
            friends.add(c.getString(0));
        }
        c.close();
        return friends;

    }


}
