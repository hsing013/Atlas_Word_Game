package com.example.harsh101.atlas;


import android.os.CountDownTimer;
import android.widget.Toast;

import java.util.HashSet;

public class Game {
    public boolean inGame = false;
    public CountDownTimer timer;
    public boolean myTurn = false;
    public GameFrag gameFrag = null;
    public String myWord = "";
    public HashSet<String> original = null;
    public HashSet<String> usedWords = null;
    public MainActivity m;


    Game(MainActivity main){
        usedWords = new HashSet<>(50);
        m = main;
        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished / 1000;
                gameFrag.setTimer(Integer.toString(seconds));
            }

            @Override
            public void onFinish() {
                gameFrag.staleMate();
            }
        };

    }

    public void recievedWord(String word){

    }

    public void setMyWord(String s){
        myWord = s;
    }

    public boolean checkWord(String word){
<<<<<<< HEAD
        // c = getApplicationContext();
        return true;
=======
        if(original.contains(word)) {
            if(usedWords.contains(word)) {
                Toast.makeText(m.getApplicationContext(), "Word already used!", Toast.LENGTH_LONG).show();
                return false;
            }
            else {
                usedWords.add(word);
                return true;
            }
        }
        else {
            Toast.makeText(m.getApplicationContext(), "Word does not exist", Toast.LENGTH_LONG).show();
            return false;
        }
>>>>>>> f1e4b9dbce3f27f0d7d2276d812e7bde8809518a
    }
    public void startTimer(){
        //++timerTurn;
        timer.start();
    }
    public void stopTimer(){
        timer.cancel();
    }
    public void setHashSet(HashSet<String> h) {
        original = h;
    }
}

