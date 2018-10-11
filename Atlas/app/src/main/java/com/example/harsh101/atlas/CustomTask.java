package com.example.harsh101.atlas;

import java.util.ArrayList;

public class CustomTask {
    private String TAG;
    private String userName;
    private String pass;
    public boolean done;
    public String message;

    public CustomTask(){
        TAG = null;
        userName = null;
        pass = null;
        done = false;
        message = "";
    }

    public void setTAG(String tag){
        TAG = tag;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTAG() {
        return TAG;
    }

    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return pass;
    }

    public String prepareLogin(){
        if (userName == null || pass == null){
            return null;
        }

        return "661" + userName + " " + pass; //when the server sees 661, it knows to login
    }
    public String prepareSignup(){
        if (userName == null || pass == null){
            return null;
        }

        return "662" + userName + " " + pass; //when the server sees 662, it knows to signup
    }

    public String getMessage(){
        return TAG + message;
    }
}
