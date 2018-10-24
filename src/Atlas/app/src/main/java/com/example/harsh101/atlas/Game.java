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
        usedWords.add(word);
        setMyWord(word);
    }

    public void setMyWord(String s){
        myWord = s;
    }

    public boolean checkWord(String word){


        if(original.contains(word)) {
            if(usedWords.contains(word)) {
                Toast.makeText(m.getApplicationContext(), "Word already used!", Toast.LENGTH_LONG).show();
                return false;
            }
            else if (myWord.charAt(myWord.length() - 1) != word.charAt(0)){
                Toast.makeText(m.getApplicationContext(), "Word must start with " + myWord.charAt(myWord.length() - 1), Toast.LENGTH_LONG).show();
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

