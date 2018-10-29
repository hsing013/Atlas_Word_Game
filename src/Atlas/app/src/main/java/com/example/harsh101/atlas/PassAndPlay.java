 package com.example.harsh101.atlas;

import android.os.CountDownTimer;
import android.widget.Toast;

import java.util.HashSet;

public class PassAndPlay {
    public CountDownTimer gameTime;
    public boolean myTurn;
    public String wordPlayed;
    public PassAndPassFrag pFrag = null;
    public HashSet<String> wordsUsed = null;
    public HashSet<String> allWords = null;
    public String myWord = "";
    public MainActivity mainA;


    PassAndPlay(MainActivity main)
    {
        wordsUsed = new HashSet<>(50);//set size to 50
        mainA = main;
        gameTime = new CountDownTimer(15000, 1000)
        {//set timer to 15 with 1 second countdown
            @Override
            public void onTick(long untilDone) {//How long until timer is done
                int seconds = (int) untilDone / 1000;
                pFrag.setTimer(Integer.toString(seconds));
            }

            @Override
            public void onFinish()
            {
                pFrag.endGame();
            }
        };
    }

    public void setWord(String sWord)
    {
        myWord = sWord;
    }

    public void wordMade(String word)
    {
        wordsUsed.add(word);
        setWord(word);
    }

    public boolean checkWord(String chWord)
    {
        if (allWords.contains(chWord))
        {
            if (wordsUsed.contains(chWord))
            {
                Toast.makeText(mainA.getApplicationContext(), "Word Already used!", Toast.LENGTH_LONG).show();
                return false;
            }

            else if (myWord.charAt(myWord.length() - 1) != chWord.charAt(0))
            {
                Toast.makeText(mainA.getApplicationContext(), "Word must start with " + myWord.charAt(myWord.length() - 1), Toast.LENGTH_LONG).show();
                return false;
            }

            else
            {
                wordsUsed.add(chWord);
                return true;
            }
        }

        else
        {
            Toast.makeText(mainA.getApplicationContext(), "Word does not exist", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    public void startTimer()
    {
        gameTime.start();
    }

    public void stopTimer()
    {
        gameTime.cancel();
    }

    public void setHashSet(HashSet<String> hSet)
    {
        allWords = hSet;
    }
}

