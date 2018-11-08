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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.crypto.spec.DHGenParameterSpec;

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
    public Game friendGame = null;
    public Game offlineGame = null;
    public Game aiGame = null;
    public Lock lock = new ReentrantLock();
    public String wordOfTheDay = "hoopla"; //will change later
    public LeaderboardFrag leaderboardFrag = null;
    public Thread serverThread = null;
    public boolean userLoggedIn = false;
    public HashSet<String> wordTable = null;
    public DataBase db = null;
    public boolean isInBackground;
    public FriendFrag friendFrag = null;
    public LeaderboardAdapter leaderboardAdapter = null;
    public AddFriendFrag addFriendFrag = null;
    public NotificationFrag notificationFrag = null;
    public SettingFrag settingFrag = null;
    public PassAndPlayFrag passAndPlayFrag = null;
    public AIGameFrag aiGameFrag = null;
    //<$NOTIFICATION$>FRIEND NAME


    public static class MyHandler extends Handler {  //this allows the serverThread talk with the mainThread(UI thread)
        private MainActivity myActivity;

        public MyHandler(MainActivity instance) {
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
                case initial_Login: {
                    break;
                }
                case initial_Signup: {
                    break;
                }
                case loginAttempt: {
                    if (s.compareTo("HOST DISCONNECTED") == 0) {
                        Toast.makeText(m.getApplicationContext(), "Lost Connection with the server", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Login;
                        m.loginTemp = null;
                        m.setFragment(m.loginFrag);
                        m.loginFrag.disableButton(false);
                        m.userLoggedIn = false;
                        m.c.closeSocket();
                    } else if (s.compareTo("<$LOGIN$>-1") == 0) {
                        Toast.makeText(m.getApplicationContext(), "User doesn't exist.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Login;
                        m.loginTemp = null;
                        m.setFragment(m.loginFrag);
                        m.loginFrag.disableButton(false);
                    } else if (s.compareTo("<$LOGIN$>-2") == 0) {
                        Toast.makeText(m.getApplicationContext(), "Password is incorrect.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Login;
                        m.loginTemp = null;
                        m.setFragment(m.loginFrag);
                        m.loginFrag.disableButton(false);
                    } else if (s.compareTo("<$LOGIN$>-4") == 0) {
                        Toast.makeText(m.getApplicationContext(), "Some unknown error occured.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Login;
                        m.loginTemp = null;
                        m.setFragment(m.loginFrag);
                        m.loginFrag.disableButton(false);
                    } else if (s.compareTo("<$LOGIN$>-3") == 0) {
                        Toast.makeText(m.getApplicationContext(), "User is already signed in.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Login;
                        m.loginTemp = null;
                        m.setFragment(m.loginFrag);
                        m.loginFrag.disableButton(false);
                    } else if (s.compareTo("<$LOGIN$>1") == 0) {
                        Toast.makeText(m.getApplicationContext(), "Sign in success!", Toast.LENGTH_LONG).show();
                        m.enterApp();
                        m.state = State.loggedIn;
                        m.loginTemp = null;
                        m.userLoggedIn = true;
                    } else if (s.contains("<$POINTS$>")) {
                        int index = s.indexOf(">");
                        int points = Integer.parseInt(s.substring(index + 1));
                        m.c.setMyPoints(points);
                        m.db.updateConfig(m.c.getUserName(), m.c.getPass(), m.c.getMyPoints());
                    }

                    break;
                }
                case signupAttempt: {
                    if (s.compareTo("<$SIGNUP$>1") == 0) {
                        Toast.makeText(m.getApplicationContext(), "Sign up Success!", Toast.LENGTH_LONG).show();
                        m.enterApp();
                        m.state = State.loggedIn;
                        m.userLoggedIn = true;
                        m.signupTemp = null;
                        m.db.updateConfig(m.c.getUserName(), m.c.getPass(), m.c.getMyPoints());
                    } else if (s.compareTo("<$SIGNUP$>-1") == 0) {
                        Toast.makeText(m.getApplicationContext(), "User exists.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Signup;
                        m.signupTemp = null;
                        m.loginFrag.disableButton(false);
                    } else if (s.compareTo("<$SIGNUP$>-2") == 0) {
                        Toast.makeText(m.getApplicationContext(), "Unknown error occured.", Toast.LENGTH_LONG).show();
                        m.state = State.initial_Signup;
                        m.signupTemp = null;
                        m.loginFrag.disableButton(false);
                    } else if (s.contains("<$POINTS$>")) {
                        int index = s.indexOf(">");
                        int points = Integer.parseInt(s.substring(index + 1));
                        m.c.setMyPoints(points);
                        m.db.updateConfig(m.c.getUserName(), m.c.getPass(), m.c.getMyPoints());
                    }
                    break;
                }
                case loggedIn: {
                    if (s.compareTo("<$GAME$>$DISCONNECTED$") == 0) {
                        if (m.onLineGame != null) {
                            m.onLineGame = null;
                            m.screen.exposeMenu();
                            m.screen.myBanner.setText("Game disconnected!");
                            m.setFragment(m.screen);
                        }
                    } else if (s.compareTo("HOST DISCONNECTED") == 0) {
                        if (m.onLineGame != null) {
                            m.onLineGame.stopTimer();
                            m.onLineGame = null;
                            m.gameFrag.endGame();
                            m.gameFrag.setOther("Lost connection with server.");
                        }
                        m.userLoggedIn = false;
                        m.hostConnected = false;
                        m.c.closeSocket();
                        //m.c.setMessageBuffer("");
                    } else if (s.compareTo("<$LOGIN$>1") == 0) {
                        m.loginTemp = null;
                        m.userLoggedIn = true;
                    } else if (s.compareTo("<$LOGIN$>-1") == 0 || s.compareTo("<$LOGIN$>-2") == 0 || s.compareTo("<$LOGIN$>-3") == 0 || s.compareTo("<$LOGIN$>-4") == 0) {
                        m.loginTemp = null;
                    } else if (s.compareTo("<$GAME$>$NOTFOUND$") == 0) {
                        if (m.onLineGame != null) {
                            m.onLineGame = null;
                            m.screen.exposeMenu();
                            m.screen.myBanner.setText("Couldn't find a game!");
                        }
                    }
                    else if (s.compareTo("<$GAME$>$FAIL_TO_INVITE$") == 0){
                        if (m.onLineGame != null) {
                            m.onLineGame = null;
                            m.screen.exposeMenu();
                            m.screen.myBanner.setText("Invitation failed!");
                        }
                    }
                    else if (s.compareTo("<$GAME$>$DENIED$") == 0){
                        if (m.onLineGame != null) {
                            m.onLineGame = null;
                            m.screen.exposeMenu();
                            m.screen.myBanner.setText("Other player denied the invitation.");
                        }
                    }
                    else if (s.compareTo("<$GAME$>$NOT_JOIN$") == 0){
                        if (m.onLineGame != null) {
                            m.onLineGame = null;
                            m.screen.exposeMenu();
                            m.screen.myBanner.setText("Player did not join the game in 30 seconds");
                        }
                    }
                    else if (s.compareTo("<$GAME$>$GAME_NOT_VALID$") == 0){
                        if (m.onLineGame != null) {
                            m.onLineGame = null;
                            m.screen.exposeMenu();
                            m.screen.myBanner.setText("Game is not valid.");
                        }
                    }
                    else if (s.compareTo("<$GAME$>$first$") == 0) {
                        if (m.onLineGame != null) {
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
                    } else if (s.compareTo("<$GAME$>$second$") == 0) {
                        if (m.onLineGame != null) {
                            System.out.println("I was triggered2.");
                            m.onLineGame.myTurn = false;
                            m.setFragment(m.gameFrag);
                            System.out.println("i came out2");
                            m.gameFrag.otherPlayerTurn();
                            m.gameFrag.setOther("Other players' turn.");
                            m.gameFrag.setTimer("15");
                            //m.onLineGame.startTimer();

                        }
                    } else if (s.compareTo("<$GAME$>$lost$") == 0) {
                        if (m.onLineGame != null) {
                            m.onLineGame.stopTimer();
                            m.gameFrag.setOther("You lost!");
                            m.gameFrag.endGame();
                        }
                        m.onLineGame = null;
                    } else if (s.compareTo("<$GAME$>$won$") == 0) {
                        if (m.onLineGame != null) {
                            m.onLineGame.stopTimer();
                            m.gameFrag.setOther("You won!");
                            m.gameFrag.endGame();
                            m.c.addToPoints(100);
                            m.db.updateConfig(m.c.getUserName(), m.c.getPass(), m.c.getMyPoints());
                        }
                        m.onLineGame = null;
                    } else if (s.compareTo("<$GAME$>$won2$") == 0) {
                        if (m.onLineGame != null) {
                            m.onLineGame.stopTimer();
                            m.gameFrag.setOther("Other user disconnected. Maybe you are too good!");
                            m.gameFrag.endGame();
                            m.c.addToPoints(50);
                            m.db.updateConfig(m.c.getUserName(), m.c.getPass(), m.c.getMyPoints());
                        }
                        m.onLineGame = null;
                    } else if (s.compareTo("<$GAME$>$won3$") == 0) {
                        if (m.onLineGame != null) {
                            m.onLineGame.stopTimer();
                            m.gameFrag.setOther("Other user quit. You are really good!");
                            m.gameFrag.endGame();
                            m.c.addToPoints(50);
                            m.db.updateConfig(m.c.getUserName(), m.c.getPass(), m.c.getMyPoints());
                        }
                        m.onLineGame = null;
                    } else if (s.contains("<$GAMEW$>")) {
                        if (m.onLineGame != null) {
                            m.onLineGame.myTurn = true;
                            m.onLineGame.stopTimer();
                            int index = s.indexOf(">");
                            String word = s.substring(index + 1);
                            m.onLineGame.recievedWord(word);
                            //m.onLineGame.setMyWord(word);
                            m.gameFrag.setOther("Your word: " + word);
                            m.gameFrag.myTurn();
                            //m.onLineGame.startTimer();
                            m.gameFrag.setTimer("15");
                            m.onLineGame.startTimer();
                        }
                    } else if (s.compareTo("<$GAME$>$startTimer$") == 0) {
                        if (m.onLineGame != null) {
                            m.onLineGame.startTimer();
                        }
                    } else if (s.contains("<$POINTS$>")) {
                        int index = s.indexOf(">");
                        int points = Integer.parseInt(s.substring(index + 1));
                        m.c.setMyPoints(points);
                        m.db.updateConfig(m.c.getUserName(), m.c.getPass(), m.c.getMyPoints());
                    } else if (s.contains("<$LEADER$>")) {
                        int index = s.indexOf(">");
                        String sub = s.substring(index + 1);
                        String players[] = sub.split("\\$");

                        ArrayList<LeaderboardUser> leaderBoard = new ArrayList<>();

                        for (int i = 0; i < players.length; ++i) {
                            String current = players[i];
                            String split[] = current.split("\\-");
                            System.out.println("Size of split: " + split.length);
                            LeaderboardUser user = new LeaderboardUser();
                            user.name = split[0];
                            user.numPoints = Integer.parseInt(split[1]);
                            leaderBoard.add(user);
                        }
                        System.out.println("Size of leaderboard list: " + leaderBoard.size());
                        m.leaderboardFrag.setList(leaderBoard);

                    } else if (s.contains("<$NOTIFICATION$>")) {
                        int index = s.indexOf(">");
                        String sub = s.substring(index + 1);
                        System.out.println("Split: " + sub);
                        String split[] = sub.split("\\-");
                        Notification n = new Notification(split[1], split[0]);
                        if (split[1].equals("$GAME_INVITE$")){
                            n.gameID = split[2];
                        }
                        m.notificationFrag.addToList(n);
                    } else if (s.contains("<$ADF$>")) {
                        int index = s.indexOf(">");
                        String sub = s.substring(index + 1);
                        String furtherSplit[] = sub.split("\\-");
                        if (furtherSplit.length == 2) {
                            int points = Integer.parseInt(furtherSplit[1]);
                            System.out.println("ADF " + points);
                            if (points == 1) {
                                m.addFriendFrag.setBanner(furtherSplit[0] + " does not exist", false, "");
                            }
                            else if (points == 2) {
                                m.addFriendFrag.setBanner(furtherSplit[0] + ": Friend request sent!", false, "");
                            }
                            else if (points == 3){
                                m.addFriendFrag.setBanner(furtherSplit[0] + ": Check your notifications!", false, "");
                            }
                            else if (points == 4){
                                m.addFriendFrag.setBanner(furtherSplit[0] + ": Already your friend!", false, "");
                            }
                            else if (points == 5){
                                m.addFriendFrag.setBanner(furtherSplit[0] + ": That is you!", false, "");
                            }
                            else{
                                m.addFriendFrag.setBanner(furtherSplit[0], true, "Add Friend");
                            }

                        }
                    } else if (s.contains("<$FRIENDLIST$>")) {
                        int index = s.indexOf(">");
                        String sub = s.substring(index + 1);
                        String split[] = sub.split("\\-");
                        ArrayList<Friend> myFriends = new ArrayList<>();
                        for (int i = 0; i < split.length; ++i) {
                            if (split[i].equals("") || split[i].contains(" ")){
                                System.out.println("Friend was empty");
                                continue;
                            }
                            Friend f = new Friend();
                            f.name = split[i];
                            myFriends.add(f);
                        }
                        m.friendFrag.setList(myFriends);
                    } else if (s.contains("<$NEWFRIEND$>")) {
                        int index = s.indexOf(">");
                        String sub = s.substring(index + 1);
                        Friend f = new Friend();
                        f.name = sub;
                        m.friendFrag.addToList(f);
                    }
                    break;
                }
                default:
                    break;
            }
            m.lock.unlock();


        }
    }

    ;

    public void enterApp() {
        loginFrag.disableButton(false);
        navigation.setVisibility(View.VISIBLE);
        setFragment(home);
    }

    public MyHandler handler = new MyHandler(this);

    public class MyRunnable implements Runnable {
        public boolean kill = false;

        public MyRunnable() {
            kill = false;
        }

        public void sendMessage(String s) {  //send message to main thread (UI thread)
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
                while (isInBackground) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                hostConnected = c.isConnectedToHost();
                while (!hostConnected) {
                    if (isInBackground) {
                        System.out.println("in background");
                        break;
                    }
                    System.out.println("Connecting-------------------------------------");
                    loginTemp = null;
                    signupTemp = null;
                    sendMessage("HOST DISCONNECTED");
                    try {
                        System.out.print("I am sleeping46\n");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hostConnected = c.connectToHost();
                    if (hostConnected) {
                        sendMessage("HOST CONNECTED");
                        try {
                            System.out.print("I am sleeping23\n");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            System.out.print("I am sleeping\n");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (isInBackground) {
                    continue;
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
                        if (!userLoggedIn && loginTemp == null && hostConnected) {
                            System.out.print("I was triggered!");
                            loginTemp = new CustomTask();
                            loginTemp.setUserName(c.getUserName());
                            loginTemp.setPass(c.getPass());
                            boolean flag = c.sendMessage(loginTemp.prepareLogin());
                            if (!flag) {
                                loginTemp = null;
                            }
                            break;
                        }
                        for (int i = 0; i < list.size(); ++i) {
                            boolean flag = c.sendMessage(list.get(i).getMessage());
                            if (!flag) {
                                System.out.print("Flag was triggered\n");
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
                                db.updateConfig(loginTemp.getUserName(), loginTemp.getPass(), 0);
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
                                db.updateConfig(signupTemp.getUserName(), signupTemp.getPass(), 0);
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
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        public void setKill(boolean kill) {
            this.kill = kill;
        }
    }

    public void readWordFile() {
        BufferedReader reader = null;
        wordTable = new HashSet<>();
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("words.txt")));

            String input = "";

            while ((input = reader.readLine()) != null) {
                if (input == "" || input.contains(" ")) {
                    continue;
                }
                wordTable.add(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
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
                    setFragment(notificationFrag);
                    return true;
                case R.id.settings:
                    setFragment(settingFrag);
                    return true;
                case R.id.friendList:
                    setFragment(friendFrag);
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment frag) {
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


        isInBackground = false;


        db = new DataBase(getApplicationContext());

        gameFrag = new GameFrag();

        ArrayList<LeaderboardUser> leaderList = new ArrayList<>();

        LeaderboardUser tempUser = new LeaderboardUser();

        tempUser.setName("Harsh");

        tempUser.setNumPoints(10000);

        leaderList.add(tempUser);

        leaderboardAdapter = new LeaderboardAdapter(this, R.layout.leaderboard_list, leaderList);

        leaderboardFrag = new LeaderboardFrag();

        leaderboardFrag.setList(leaderList);
        leaderboardFrag.adapter = leaderboardAdapter;

        readWordFile();

        addFriendFrag = new AddFriendFrag();

        passAndPlayFrag = new PassAndPlayFrag();

        c = new Client();

        settingFrag = new SettingFrag();

        list = new ArrayList<>();

        friendFrag = new FriendFrag();
        friendFrag.main = this;
        ArrayList<Friend> friends = new ArrayList<>();
        FriendArrayAdapter friendArrayAdapter = new FriendArrayAdapter(this, R.layout.friend_element, friends);
        friendFrag.setList(friends);
        friendFrag.adapter = friendArrayAdapter;

        serverThread = new Thread(new MyRunnable());

        loginFrag = new LoginFrag();
        loginFrag.m = this;

        screen = new LoadingScreen();

        home = new HomeFrag();

        notificationFrag = new NotificationFrag();

        ArrayList<Notification> notifications = new ArrayList<>();

        NotificationAdapter adapter = new NotificationAdapter(this, R.layout.notification_element, notifications);

        notificationFrag.setList(notifications);

        notificationFrag.adapter = adapter;

        ArrayList<String> config = db.getConfig();



        hostConnected = false;

        if (config != null) {
            c.setUserName(config.get(0));
            c.setPass(config.get(1));
            c.setMyPoints(Integer.valueOf(config.get(2)).intValue());
            state = State.loggedIn;
            returnHome(null);
        } else {
            setFragment(loginFrag);
        }

        System.out.println("I am leaving");

        serverThread.start();

    }


    public void onDestroy() {
        super.onDestroy();
        c.closeSocket();
        db.updateConfig(c.getUserName(), c.getPass(), c.getMyPoints());
        System.out.println("On destroy was triggered.");
    }

    public void onPause() {
        super.onPause();
        c.closeSocket();
        c.setMessageBuffer("");
        isInBackground = true;
        if (screen != null && screen.isVisible() && state == State.loggedIn) {
            if (home != null) {
                setFragment(home);
            }
        }
        db.updateConfig(c.getUserName(), c.getPass(), c.getMyPoints());
        System.out.println("On pause was triggered.");
    }

    public void onStart() {
        super.onStart();
        isInBackground = false;
        System.out.println("OnStart was triggered.");
        if (serverThread != null && !serverThread.isAlive()) {
            System.out.println("onstart if");
            serverThread = new Thread(new MyRunnable());
            serverThread.start();
        }
    }

    public void onResume() {
        super.onResume();
        System.out.println("On resume was triggered.");
        isInBackground = false;
        if (serverThread != null && !serverThread.isAlive()) {
            System.out.println("onresume if");
            serverThread = new Thread(new MyRunnable());
            serverThread.start();
        }
    }


    public void loginButtonClick(View v) {
        System.out.println("Button clicked");
        if (!hostConnected) {
            Toast.makeText(getApplicationContext(), "Not connected to server", Toast.LENGTH_LONG).show();
            return;
        }
        loginFrag.disableButton(true);
        ArrayList<String> info = loginFrag.readInput();
        if (info != null) {
            if (state == State.initial_Login) {
                loginTemp = new CustomTask();
                loginTemp.setTAG("LOGIN");
                loginTemp.setUserName(info.get(0));
                loginTemp.setPass(info.get(1));
                state = State.loginAttempt;
            } else if (state == State.initial_Signup) {
                signupTemp = new CustomTask();
                signupTemp.setTAG("LOGIN");
                signupTemp.setUserName(info.get(0));
                signupTemp.setPass(info.get(1));
                state = State.signupAttempt;
            }
        } else {
            loginFrag.disableButton(false);
        }
    }


    /*If the user hits the looking for signup
        text then it switches them to signup screen
        but if the click it again, it will take them back to the
        login screen
     */
    public void onSignUpClick(View v) {
        System.out.print("I work!");
        if (state == State.initial_Login) {
            state = State.initial_Signup;
            loginFrag.flipToSignUp(true);
        } else {
            state = State.initial_Login;
            loginFrag.flipToSignUp(false);
        }
    }

    public void onPlayOnline(View v) {
        if (!hostConnected) {
            Toast.makeText(getApplicationContext(), "Not connected to server.", Toast.LENGTH_LONG).show();
            return;
        } else if (!userLoggedIn) {
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_LONG).show();
            return;
        } else if (onLineGame != null) {
            //TODO
        }

        setFragment(screen);
        navigation.setVisibility(View.INVISIBLE);
        onLineGame = new Game(this);
        onLineGame.setHashSet(wordTable);
        onLineGame.gameFrag = gameFrag;
        gameFrag.currentGame = onLineGame;
        lock.lock();
        CustomTask task = new CustomTask();
        task.setTAG("<$GAME$>");
        task.message = "enqueue";
        list.add(task);
        lock.unlock();
        gameFrag.reset();


    }

    public void onGameSend(View v) {
        if (onLineGame != null) {
            String word = gameFrag.getInput();
            boolean check = onLineGame.checkWord(word);
            if (check) {
                onLineGame.stopTimer();
                CustomTask task = new CustomTask();
                task.setTAG("<$GAMEW$>");
                task.message = word;
                lock.lock();
                list.add(task);
                lock.unlock();
                gameFrag.otherPlayerTurn();
                gameFrag.setOther("Other players' turn");
                gameFrag.setTimer("15");
                onLineGame.startTimer();
            } else {
                gameFrag.setButton(true);
                gameFrag.setInputUser(true);
            }
        }
        else if (offlineGame != null){
            String word = passAndPlayFrag.getInput();
            boolean check = offlineGame.checkWord(word);
            if (check){
                offlineGame.stopTimer();
                offlineGame.recievedWord(word);
                offlineGame.setMyWord(word);
                passAndPlayFrag.myTurn();
                if (offlineGame.player1Turn) {
                    passAndPlayFrag.setOther("Player 2's word: " + word);
                    offlineGame.player1Turn = false;
                }
                else{
                    passAndPlayFrag.setOther("Player 1's word: " + word);
                    offlineGame.player1Turn = true;
                }
                passAndPlayFrag.setTimer("15");
                offlineGame.startTimer();
            }
            else{
                passAndPlayFrag.setButton(true);
                passAndPlayFrag.setInputUser(true);
            }
        }

        else if (aiGame != null)
        {
            String word = aiGameFrag.getInput();
            boolean check = aiGame.checkWord(word);
            if(check)
            {
                aiGame.stopTimer();
                aiGame.recievedWord(word);
                aiGame.setMyWord(word);
                aiGameFrag.myTurn();
            if (aiGame.player1Turn) {
                aiGameFrag.setOther("AI's word: " + word);
                aiGame.player1Turn = false;
            }
            else{
                aiGameFrag.setOther("Player's word: " + word);
                aiGame.player1Turn = true;
            }
            aiGameFrag.setTimer("15");
            aiGame.startTimer();
            }
            else{
                aiGameFrag.setButton(true);
                aiGameFrag.setInputUser(true);
            }
        }

    }

    public void returnHome(View v) {
        setFragment(home);
        navigation.setVisibility(View.VISIBLE);
    }

    public void showPoints(View v) { // this is temporary
        Toast.makeText(getApplicationContext(), Integer.toString(c.getMyPoints()), Toast.LENGTH_LONG).show();
    }

    public void onAddFriend(View v) {
       setFragment(addFriendFrag);
    }

    public void backButtonAddFriend(View v) {
        addFriendFrag.reset();
        setFragment(friendFrag);
    }

    public void searchUser(View v) {
        if (!hostConnected) {
            Toast.makeText(getApplicationContext(), "Not connected to server.", Toast.LENGTH_LONG).show();
            return;
        } else if (!userLoggedIn) {
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println("Search user was clicked");
        addFriendFrag.searchButton.setEnabled(false);
        CustomTask customTask = new CustomTask();
        customTask.setTAG("<$ADF$>");
        customTask.message = addFriendFrag.input.getText().toString();
        addFriendFrag.reset();
        addFriendFrag.banner.setText("Searching...");
        addFriendFrag.searchButton.setEnabled(false);
        lock.lock();
        list.add(customTask);
        lock.unlock();
    }

    public void sendFriendRequest(View v) {
        if (!hostConnected) {
            Toast.makeText(getApplicationContext(), "Not connected to server.", Toast.LENGTH_LONG).show();
            return;
        } else if (!userLoggedIn) {
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_LONG).show();
            return;
        }
        CustomTask customTask = new CustomTask();
        customTask.setTAG("<$ADD$>");
        customTask.message = addFriendFrag.banner.getText().toString();
        addFriendFrag.reset();
        lock.lock();
        list.add(customTask);
        lock.unlock();
        Toast.makeText(getApplicationContext(), "Friend Request Sent!", Toast.LENGTH_LONG).show();
    }

    public void yesToNotification(View v){
        if (!hostConnected) {
            Toast.makeText(getApplicationContext(), "Not connected to server.", Toast.LENGTH_LONG).show();
            return;
        } else if (!userLoggedIn) {
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_LONG).show();
            return;
        }
        TextView view = (TextView) v;
        int pos = (int) view.getTag();
        Notification n = notificationFrag.getAndRemove(pos);
        if (n != null){
            CustomTask customTask = new CustomTask();
            customTask.setTAG("<$NOTIFICATION$>");
            if (n.type.equals("$GAME_INVITE$")) {
                customTask.message = n.from + " YES " + n.type + " " + n.gameID;
                setFragment(screen);
                navigation.setVisibility(View.INVISIBLE);
                onLineGame = new Game(this);
                onLineGame.setHashSet(wordTable);
                onLineGame.gameFrag = gameFrag;
                gameFrag.currentGame = onLineGame;
                gameFrag.reset();
            }
            else{
                customTask.message = n.from + " YES " + n.type;
            }
            lock.lock();
            list.add(customTask);
            lock.unlock();
        }
    }

    public void noToNotification(View v){
        if (!hostConnected) {
            Toast.makeText(getApplicationContext(), "Not connected to server.", Toast.LENGTH_LONG).show();
            return;
        } else if (!userLoggedIn) {
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_LONG).show();
            return;
        }
        TextView view = (TextView) v;
        int pos = (int) view.getTag();
        Notification n = notificationFrag.getAndRemove(pos);
        if (n != null){
            CustomTask customTask = new CustomTask();
            customTask.setTAG("<$NOTIFICATION$>");
            if (n.type.equals("$GAME_INVITE$")) {
                customTask.message = n.from + " NO " + n.type + " " + n.gameID;
            }
            else{
                customTask.message = n.from + " NO " + n.type;
            }
            lock.lock();
            list.add(customTask);
            lock.unlock();
        }
    }

    public void signOut(View v){
        state = State.initial_Login;
        userLoggedIn = false;
        navigation.setVisibility(View.INVISIBLE);
        //setFragment(screen);
        c.clientLock.lock();
        c.setUserName("");
        c.setPass("");
        c.clientLock.unlock();
        c.closeSocket();
        leaderboardFrag.signOut();
        friendFrag.signOut();
        notificationFrag.signOut();
        loginFrag.flipToSignUp(false);
        setFragment(loginFrag);

        db.deleteAllTables(getApplicationContext());

        db = new DataBase(getApplicationContext());


    }

    public void playWithFriend(View v){
        if (!hostConnected) {
            Toast.makeText(getApplicationContext(), "Not connected to server.", Toast.LENGTH_LONG).show();
            return;
        } else if (!userLoggedIn) {
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_LONG).show();
            return;
        }
        gameFrag.reset();
        System.out.print("Play with friend was called!");
        setFragment(screen);
        navigation.setVisibility(View.INVISIBLE);
        //screen.myBanner.setText("Waiting for friend to join...30 seconds");
        onLineGame = new Game(this);

        onLineGame.gameFrag = gameFrag;
        onLineGame.setHashSet(wordTable);

        String name = (String) v.getTag();

        System.out.println("Name of friend is " + name);

        CustomTask newTask = new CustomTask();

        newTask.setTAG("<$GAMEI$>");
        newTask.message = name;
        lock.lock();
        list.add(newTask);
        lock.unlock();

    }

    public void passAndPlay(View v){
        offlineGame = new Game(this);
        navigation.setVisibility(View.INVISIBLE);
        passAndPlayFrag.reset();
        passAndPlayFrag.offlineGame = offlineGame;
        offlineGame.passAndPlay = true;
        offlineGame.player1Turn = true;
        offlineGame.passAndPlayFrag = passAndPlayFrag;
        offlineGame.setHashSet(wordTable);
        offlineGame.setMyWord(wordOfTheDay);
        passAndPlayFrag.setOther("Player 1's word: " + wordOfTheDay);
        passAndPlayFrag.setTimer("15");
        offlineGame.startTimer();
        setFragment(passAndPlayFrag);

    }

    public void AIGAME(View v)
    {
        aiGame = new Game(this);
        navigation.setVisibility(View.INVISIBLE);
        aiGameFrag.reset();
        aiGameFrag.aiGame = aiGame;
        aiGame.aiBGame = true;
        aiGame.player1Turn = true;
        aiGame.aiGameFrag = aiGameFrag;
        aiGame.setHashSet(wordTable);
        aiGame.setMyWord("Player's word: " + wordOfTheDay);
        aiGame.startTimer();
        setFragment(aiGameFrag);
    }

}
