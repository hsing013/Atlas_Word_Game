package com.example.harsh101.atlas;


import android.os.CountDownTimer;
import android.widget.Toast;

import java.util.HashSet;

public class Game {
    public boolean inGame = false;
    public CountDownTimer timer;
    public boolean myTurn = false;
    public boolean passAndPlay = false;
    public boolean aiBGame = false;
    public GameFrag gameFrag = null;
    public String myWord = "";
    public HashSet<String> original = null;
    public HashSet<String> usedWords = null;
    public MainActivity m;
    public boolean player1Turn = false;
    public PassAndPlayFrag passAndPlayFrag = null;
    public AIGameFrag aiGameFrag = null;
    public AI aiBot = null;

    Game(){
        m = null;
    }

    Game(MainActivity main){
        usedWords = new HashSet<>(50);
        m = main;
        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished / 1000;
                if (gameFrag != null) {
                    gameFrag.setTimer(Integer.toString(seconds));
                }
                else if (passAndPlayFrag != null){
                    passAndPlayFrag.setTimer(Integer.toString(seconds));
                }
                else if (aiGameFrag != null)
                {
                    aiGameFrag.setTimer(Integer.toString(seconds));
                }
            }

            @Override
            public void onFinish() {
                if (!passAndPlay && aiGameFrag == null) {
                    gameFrag.staleMate();
                }
                else if (passAndPlay){
                    passAndPlayFrag.endGame();
                }
                else if (aiGameFrag != null){
                    aiGameFrag.endGame();
                }
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
                System.out.print("Word1");
                Toast.makeText(m.getApplicationContext(), "Word already used!", Toast.LENGTH_LONG).show();
                return false;
            }
            else if (myWord.charAt(myWord.length() - 1) != word.charAt(0)){
                System.out.print("Word2");
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
            System.out.print("Word3");
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

