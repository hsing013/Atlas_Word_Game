package com.example.harsh101.atlas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AIGameFrag extends GameFrag {

    public Game aiGame = null;
    public AIGameFrag() {
        // Required empty public constructor
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        if (aiGame != null)
        {
            setOther("Your word: " + aiGame.myWord);
            myTurn();
        }
        return v;
        // Inflate the layout for this fragment
    }

    public void endGame()
    {
        if (myView == null)
        {
            return;
        }
        timer.setVisibility(View.INVISIBLE);
        inputUser.setVisibility(View.INVISIBLE);
        send.setVisibility(View.INVISIBLE);
        quit.setVisibility(View.VISIBLE);
        if (aiGame.player1Turn)
        {
            setOther("AI won!");
        }
        else
        {
            setOther("Player won!");
        }
        this.aiGame.aiBot.reset();
        this.aiGame = null;
    }

}
