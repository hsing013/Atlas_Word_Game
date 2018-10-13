package com.example.harsh101.atlas;


import android.os.CountDownTimer;

public class Game {
    public boolean inGame = false;
    public CountDownTimer timer;
    public boolean myTurn = false;
    public GameFrag gameFrag = null;
    public String myWord = "";



    Game(){
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
        // c = getApplicationContext();
        return true;
    }
    public void startTimer(){
        //++timerTurn;
        timer.start();
    }
    public void stopTimer(){
        timer.cancel();
    }
}

