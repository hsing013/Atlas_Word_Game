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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {

    enum State {initial_Login, initial_Signup, loginAttempt, signupAttempt, loggedIn, setup}

    public State state = State.initial_Login;
    CustomTask loginTemp = null;
    CustomTask signupTemp = null;
    CustomTask setupTask = null; //upcoming
    CustomTask gamePlay = null;
    boolean inGame = false;
    boolean hostConnected = false;
    public BottomNavigationView navigation = null;
    public LoginFrag loginFrag = null;
    public LoadingScreen screen = null;
    public Client c = null;
    public HomeFrag home = null;
    public GameFrag gameFrag = null;
    public ArrayList<CustomTask> list = null;
    public Game onLineGame = null;
    public Game offlineGame = null;
    public Lock lock = new ReentrantLock();
    public String wordOfTheDay = "hoopla"; //will change later
    public LeaderboardFrag leaderboardFrag = null;
    public Thread serverThread = null;
    public boolean loggedIn = false;


    public static class MyHandler extends Handler{  //this allows the serverThread talk with the mainThread(UI thread)
        private MainActivity myActivity;
        public MyHandler(MainActivity instance){
            myActivity = instance;
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity m = myActivity;
            String s = msg.getData().getString("Message");
            State currentState = myActivity.state;
            System.out.println(s);
            m.lock.lock();
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
                    if (s.compareTo("HOST DISCONNECTED") == 0){
                        Toast.makeText(m.getApplicationContext(), "Lost Connection with the server", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Login;
                        m.loginTemp = null;
                        m.setFragment(m.loginFrag);
                        m.loginFrag.disableButton(false);
                        m.loggedIn = false;
                    }
                    else if (s.compareTo("<$LOGIN$>-1") == 0){
                        Toast.makeText(m.getApplicationContext(), "User doesn't exist.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Login;
                        m.loginTemp = null;
                        m.setFragment(m.loginFrag);
                        m.loginFrag.disableButton(false);
                    }
                    else if (s.compareTo("<$LOGIN$>-2") == 0){
                        Toast.makeText(m.getApplicationContext(), "Password is incorrect.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Login;
                        m.loginTemp = null;
                        m.setFragment(m.loginFrag);
                        m.loginFrag.disableButton(false);
                    }
                    else if (s.compareTo("<$LOGIN$>-4") == 0){
                        Toast.makeText(m.getApplicationContext(), "Some unknown error occured.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Login;
                        m.loginTemp = null;
                        m.setFragment(m.loginFrag);
                        m.loginFrag.disableButton(false);
                    }
                    else if (s.compareTo("<$LOGIN$>-3") == 0){
                        Toast.makeText(m.getApplicationContext(), "User is already signed in.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Login;
                        m.loginTemp = null;
                        m.setFragment(m.loginFrag);
                        m.loginFrag.disableButton(false);
                    }
                    else if (s.compareTo("<$LOGIN$>1") == 0){
                        Toast.makeText(m.getApplicationContext(), "Sign in success!", Toast.LENGTH_LONG).show();
                        m.enterApp();
                        m.state = State.loggedIn;
                        m.loginTemp = null;
                    }
                    
                    break;
                }
                case signupAttempt:
                {
                    if (s.compareTo("<$SIGNUP$>1") == 0){
                        Toast.makeText(m.getApplicationContext(), "Sign up Success!", Toast.LENGTH_LONG).show();
                        m.enterApp();
                        m.state = State.loggedIn;
                        m.signupTemp = null;
                    }
                    else if (s.compareTo("<$SIGNUP$>-1") == 0){
                        Toast.makeText(m.getApplicationContext(), "User exists.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Signup;
                        m.signupTemp = null;
                        m.loginFrag.disableButton(false);
                    }
                    else if (s.compareTo("<$SIGNUP$>-2") == 0){
                        Toast.makeText(m.getApplicationContext(), "Unknown error occured.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Signup;
                        m.signupTemp = null;
                        m.loginFrag.disableButton(false);
                    }
                    break;
                }
                case loggedIn:
                {
                    if (s.compareTo("<$GAME$>$DISCONNECTED$") == 0){
                        if (m.onLineGame != null){
                            m.onLineGame = null;
                            m.setFragment(m.home);
                        }
                    }
                    else if (s.compareTo("HOST DISCONNECTED") == 0){
                        if (m.onLineGame != null){
                            m.onLineGame.stopTimer();
                            m.onLineGame = null;
                            m.gameFrag.endGame();
                            m.gameFrag.setOther("Lost connection with server.");
                        }
                        m.loggedIn = false;
                        m.hostConnected = false;
                        m.c.setMessageBuffer("");
                    }
                    else if (s.compareTo("<$LOGIN$>1") == 0){
                        m.loginTemp = null;
                        m.loggedIn = true;
                    }
                    else if (s.compareTo("<$LOGIN$>-1") == 0 || s.compareTo("<$LOGIN$>-2") == 0 || s.compareTo("<$LOGIN$>-3") == 0 || s.compareTo("<$LOGIN$>-4") == 0){
                        m.loginTemp = null;
                    }
                    else if (s.compareTo("<$GAME$>$NOTFOUND$") == 0){
                        if (m.onLineGame != null){
                            m.onLineGame = null;
                            m.screen.exposeMenu();
                            m.screen.myBanner.setText("Couldn't find a game!");
                        }
                    }
                    else if (s.compareTo("<$GAME$>$first$") == 0){
                        if (m.onLineGame != null){
                            System.out.println("I was triggered.");
                            m.onLineGame.myTurn = true;
                            m.onLineGame.setMyWord(m.wordOfTheDay);
                            m.setFragment(m.gameFrag);
                            System.out.println("i came out");
                            m.gameFrag.myTurn();
                            m.gameFrag.setOther("Your word: " + m.wordOfTheDay);
                            m.gameFrag.setTimer("15");
                            //m.onLineGame.startTimer();

                        }
                    }
                    else if (s.compareTo("<$GAME$>$second$") == 0){
                        if (m.onLineGame != null){
                            System.out.println("I was triggered2.");
                            m.onLineGame.myTurn = false;
                            m.setFragment(m.gameFrag);
                            System.out.println("i came out2");
                            m.gameFrag.otherPlayerTurn();
                            m.gameFrag.setOther("Other players' turn.");
                            m.gameFrag.setTimer("15");
                            //m.onLineGame.startTimer();

                        }
                    }
                    else if (s.compareTo("<$GAME$>$lost$") == 0){
                        if (m.onLineGame != null) {
                            m.onLineGame.stopTimer();
                            m.gameFrag.setOther("You lost!");
                            m.gameFrag.endGame();
                        }
                        m.onLineGame = null;
                    }
                    else if (s.compareTo("<$GAME$>$won$") == 0){
                        if (m.onLineGame != null) {
                            m.onLineGame.stopTimer();
                            m.gameFrag.setOther("You won!");
                            m.gameFrag.endGame();
                        }
                        m.onLineGame = null;
                    }
                    else if (s.compareTo("<$GAME$>$won2$") == 0){
                        if (m.onLineGame != null) {
                            m.onLineGame.stopTimer();
                            m.gameFrag.setOther("Other user disconnected. Maybe you are too good!");
                            m.gameFrag.endGame();
                        }
                        m.onLineGame = null;
                    }
                    else if (s.compareTo("<$GAME$>$won3$") == 0){
                        if (m.onLineGame != null) {
                            m.onLineGame.stopTimer();
                            m.gameFrag.setOther("Other user quit. You are really good!");
                            m.gameFrag.endGame();
                        }
                        m.onLineGame = null;
                    }
                    else if (s.contains("<$GAMEW$>")){
                        if (m.onLineGame != null){
                            m.onLineGame.myTurn = true;
                            m.onLineGame.stopTimer();
                            int index = s.indexOf(">");
                            String word = s.substring(index + 1);
                            m.onLineGame.recievedWord(word);
                            m.onLineGame.setMyWord(word);
                            m.gameFrag.setOther("Your word: " + word);
                            m.gameFrag.myTurn();
                            //m.onLineGame.startTimer();
                            m.gameFrag.setTimer("15");
                        }
                    }
                    else if (s.compareTo("<$GAME$>startTimer") == 0){
                        if (m.onLineGame != null){
                            m.onLineGame.startTimer();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
            m.lock.unlock();


        }
    };

    public void enterApp(){
        loginFrag.disableButton(false);
        navigation.setVisibility(View.VISIBLE);
        setFragment(home);
    }

    public MyHandler handler = new MyHandler(this);

    public class MyRunnable implements Runnable{
        public boolean kill = false;
        public MyRunnable(){
            kill = false;
        }

        public void sendMessage(String s){  //send message to main thread (UI thread)
            Message myMsg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("Message", s);
            myMsg.setData(bundle);
            handler.sendMessage(myMsg);
        }

        @Override
        public void run() {
            System.out.println("I am running");

            while (!kill) {
                hostConnected = c.isConnectedToHost();
                while (!hostConnected) {
                    System.out.println("Connecting");
                    loginTemp = null;
                    signupTemp = null;
                    sendMessage("HOST DISCONNECTED");
                    hostConnected = c.connectToHost();
                    if (hostConnected) {
                        sendMessage("HOST CONNECTED");

                    } else {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                lock.lock();
                switch (state) {
                    case initial_Login: {
                        break;
                    }
                    case initial_Signup: {
                        break;
                    }
                    case loginAttempt: {
                        if (loginTemp != null && !loginTemp.done) {
                            boolean ok = c.sendMessage(loginTemp.prepareLogin());
                            if (!ok) {
                                sendMessage("<$LOGIN$>-4");
                                loginTemp = null;
                            }
                            loginTemp.done = true;
                        }
                        break;
                    }
                    case signupAttempt: {
                        if (signupTemp != null && !signupTemp.done) {
                            boolean ok = c.sendMessage(signupTemp.prepareSignup());
                            if (!ok) {
                                sendMessage("<$SIGNUP$>-2");
                                signupTemp = null;
                            }
                            signupTemp.done = true;
                        }
                        break;
                    }
                    case loggedIn: {
                        if (!loggedIn && loginTemp == null){
                            loginTemp = new CustomTask();
                            loginTemp.setUserName(c.getUserName());
                            loginTemp.setPass(c.getPass());
                            c.sendMessage(loginTemp.prepareLogin());
                            break;
                        }
                        for (int i = 0; i < list.size(); ++i) {
                            boolean flag = c.sendMessage(list.get(i).getMessage());
                            if (!flag) {
                                sendMessage("HOST DISCONNECTED");
                                break;
                            }
                        }
                        list.clear();

                        break;
                    }
                    default:
                        break;
                }
                lock.unlock();
                //System.out.println("Coming out");
                ArrayList<String> messages = c.readSocket();

                if (messages == null) {
                    continue;
                }

                System.out.println("Size of: " + messages.size());

                for (int i = 0; i < messages.size(); ++i) {
                    String message = messages.get(i);
                    System.out.println(message);
                    switch (state) {
                        case initial_Login: {
                            break;
                        }
                        case initial_Signup: {
                            break;
                        }
                        case loginAttempt: {
                            if (loginTemp == null) {
                                continue;
                            }
                            if (message.compareTo("<$LOGIN$>1") == 0) {
                                c.setUserName(loginTemp.getUserName());
                                c.setPass(loginTemp.getPass());
                            }
                            sendMessage(message);
                            break;
                        }
                        case signupAttempt: {
                            if (signupTemp == null) {
                                continue;
                            }
                            if (message.compareTo("<$SIGNUP$>1") == 0) {
                                c.setUserName(signupTemp.getUserName());
                                c.setPass(signupTemp.getPass());
                            }
                            sendMessage(message);
                            break;
                        }
                        case loggedIn: {
                            sendMessage(message);
                            break;
                        }
                        default:
                            break;
                    }
                }
            }

            }

        public void setKill(boolean kill) {
            this.kill = kill;
        }
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(home);
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(leaderboardFrag);
                    return true;
                case R.id.navigation_notifications:
                    return true;
                case R.id.settings:
                    return true;
                case R.id.friendList:
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
        //System.out.println("I am here");
        setContentView(R.layout.activity_main);
        //System.out.print("I am here2");
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setVisibility(View.INVISIBLE); //initially the bottom tab bar is invisible


        //System.out.println("I am here3");

        c = new Client();

        list = new ArrayList<>();

        serverThread = new Thread(new MyRunnable());

        serverThread.start();

        loginFrag = new LoginFrag();

        screen = new LoadingScreen();

        home = new HomeFrag();

        setFragment(loginFrag);

        System.out.println("I am leaving");

        gameFrag = new GameFrag();

        leaderboardFrag = new LeaderboardFrag();

    }


    public void onDestroy(){
        super.onDestroy();
        c.closeSocket();
        System.out.println("On destroy was triggered.");
    }

    public void onPause(){
        super.onPause();
        c.closeSocket();
        c.setMessageBuffer("");
        System.out.println("On pause was triggered.");
    }

    public void onStart(){
        super.onStart();
        System.out.println("OnStart was triggered.");
        if (serverThread != null && !serverThread.isAlive()){
            serverThread = new Thread(new MyRunnable());
            serverThread.start();
        }
    }

    public void onResume(){
        super.onResume();
        System.out.println("On resume was triggered.");
        if (serverThread != null && !serverThread.isAlive()){
            serverThread = new Thread(new MyRunnable());
            serverThread.start();
        }
    }


    public void loginButtonClick(View v){
        System.out.println("Button clicked");
        if (!hostConnected){
            Toast.makeText(getApplicationContext(), "Not connected to server", Toast.LENGTH_LONG).show();
            return;
        }
        loginFrag.disableButton(true);
        ArrayList<String> info = loginFrag.readInput();
        if (info != null){
            if (state == State.initial_Login){
                loginTemp = new CustomTask();
                loginTemp.setTAG("LOGIN");
                loginTemp.setUserName(info.get(0));
                loginTemp.setPass(info.get(1));
                state = State.loginAttempt;
            }
            else if (state == State.initial_Signup){
                signupTemp = new CustomTask();
                signupTemp.setTAG("LOGIN");
                signupTemp.setUserName(info.get(0));
                signupTemp.setPass(info.get(1));
                state = State.signupAttempt;
            }
        }
        else{
            loginFrag.disableButton(false);
        }
    }



    /*If the user hits the looking for signup
        text then it switches them to signup screen
        but if the click it again, it will take them back to the
        login screen
     */
    public void onSignUpClick(View v){
        System.out.print("I work!");
        if (state == State.initial_Login){
            state = State.initial_Signup;
            loginFrag.flipToSignUp(true);
        }
        else{
            state = State.initial_Login;
            loginFrag.flipToSignUp(false);
        }
    }

    public void onPlayOnline(View v){
        if (!hostConnected){
            Toast.makeText(getApplicationContext(), "Not connected to server.", Toast.LENGTH_LONG).show();
            return;
        }
        else if (!loggedIn){
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_LONG).show();
            return;
        }
        else if (onLineGame != null){
            //TODO
        }

        setFragment(screen);
        navigation.setVisibility(View.INVISIBLE);
        onLineGame = new Game(this);
        onLineGame.gameFrag = gameFrag;
        gameFrag.currentGame = onLineGame;
        listLock.lock();
        CustomTask task = new CustomTask();
        task.setTAG("<$GAME$>");
        task.message = "enqueue";
        list.add(task);
        listLock.unlock();
        gameFrag.reset();


    }

    public void onGameSend(View v){
        if (onLineGame != null){
            String word = gameFrag.getInput();
            boolean check = onLineGame.checkWord(word);
            if (check){
                onLineGame.stopTimer();
                CustomTask task = new CustomTask();
                task.setTAG("<$GAMEW$>");
                task.message = word;
                listLock.lock();
                list.add(task);
                listLock.unlock();
                gameFrag.otherPlayerTurn();
                gameFrag.setOther("Other players' turn");
                gameFrag.setTimer("15");
                onLineGame.startTimer();
            }
            else{
                Toast.makeText(getApplicationContext(), "Choose a different word", Toast.LENGTH_LONG).show();
                gameFrag.setButton(true);
                gameFrag.setInputUser(true);
            }
        }
    }

    public void returnHome(View v){
        setFragment(home);
        navigation.setVisibility(View.VISIBLE);
    }

}
