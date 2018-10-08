package com.example.harsh101.atlas;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    enum State {initial_Login, initial_Signup, loginAttempt, signupAttempt, loggedIn}

    public State state = State.initial_Login;

    public Button login = null;
    public TextView banner = null;
    public TextView banner2 = null;
    public EditText userName = null;
    public EditText password = null;
    public TextView usernameHint = null;
    public BottomNavigationView navigation = null;
    public Client c = null;
    public boolean hostConnected = false;
    public CustomTask loginTemp = null;  //stores temporary login info
    public CustomTask signupTemp = null; //stores temporary sign up info

    private static class MyHandler extends Handler{  //this allows the serverThread talk with the mainThread(UI thread)
        private MainActivity myActivity;
        public MyHandler(MainActivity instance){
            myActivity = instance;
        }

        @Override
        public void handleMessage(Message msg) {
            String s = msg.getData().getString("Message");
            State currentState = myActivity.state;
            switch (currentState) {
                case initial_Login:
                {

                    break;
                }
                case initial_Signup:
                {
                    break;
                }
                case loginAttempt:
                {
                    if (s == "HOST DISCONNECTED"){
                        Toast.makeText(myActivity.getApplicationContext(), "Lost Connection with the server", Toast.LENGTH_LONG).show();
                        myActivity.state = State.initial_Login;
                    }
                    break;
                }
                case signupAttempt:
                {
                    break;
                }
                case loggedIn:
                {

                    break;
                }
                default:
                    break;
            }
        }
    };

    public MyHandler handler = new MyHandler(this);


    Thread serverThread = new Thread(new Runnable() { //background thread that handles reading and writing of socket

        public void sendMessage(String s){  //send message to main thread (UI thread)
            Message myMsg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("Message", s);
            myMsg.setData(bundle);
            handler.sendMessage(myMsg);
        }
        @Override
        public void run() {
            hostConnected = c.isConnectedToHost();
            while (!hostConnected){
                loginTemp = null;
                signupTemp = null;
                sendMessage("HOST DISCONNECTED");
                hostConnected = c.connectToHost();
                if (hostConnected){
                    sendMessage("HOST CONNECTED");
                }
                else{
                    try{
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }

            switch (state){
                case initial_Login:
                {
                    break;
                }
                case initial_Signup:
                {
                    break;
                }
                case loginAttempt:
                {
                    if (loginTemp != null){
                        boolean ok = c.sendMessage(loginTemp.prepareLogin());
                        if (!ok){
                            sendMessage("Login Failed(4)");
                            loginTemp = null;
                        }
                    }
                    break;
                }
                case signupAttempt:
                {
                    if (signupTemp != null){
                        boolean ok = c.sendMessage(signupTemp.prepareSignup());
                        if (!ok){
                            sendMessage("Signup Failed(1)");
                            signupTemp = null;
                        }
                    }
                    break;
                }
                case loggedIn:
                {

                    break;
                }
                default:
                    break;
            }


        }



    });

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment frag){
        FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        fragTrans.replace(R.id.mainFrame, frag);
        fragTrans.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setVisibility(View.INVISIBLE); //initially the bottom tab bar is invisible

        login = findViewById(R.id.loginButton);
        banner = findViewById(R.id.banner);
        banner2 = findViewById(R.id.signUptext);
        userName = findViewById(R.id.userNameInput);
        password = findViewById(R.id.passwordInput);
        usernameHint = findViewById(R.id.userNameHint);

        usernameHint.setVisibility(View.INVISIBLE); //initially the username hint is invisible because it belongs to the signup screen

        usernameHint.setText("Username must start with a letter and must be at least 8 letters long.\n Symbols and whitespace are not allowed.");

    }

    /*If the user hits the looking for signup
        text then it switches them to signup screen
        but if the click it again, it will take them back to the
        login screen
     */

    public void signUpClick(View v){
        if (state == State.initial_Login){
            login.setText("Signup");
            banner.setText("Signup here!");
            banner2.setText("Back to the Login?");
            userName.setText("");
            password.setText("");
            state = State.initial_Signup;
            usernameHint.setVisibility(View.VISIBLE);
        }
        else{
            login.setText("Login");
            banner.setText("Login here!");
            banner2.setText("Looking to signup?");
            userName.setText("");
            password.setText("");
            state = State.initial_Login;
            usernameHint.setVisibility(View.INVISIBLE);
        }
    }

    public void loginButtonClick(View v){

    }

}
