package com.example.harsh101.atlas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PassAndPlayFrag extends GameFrag {

    public Game offlineGame = null;

    public PassAndPlayFrag() {
        // Required empty public constructor
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        if (offlineGame != null){
            setOther("Player's 1 word: " + offlineGame.myWord);
            myTurn();
        }
        return v;
    }

    @Override
    public void endGame(){
        if (myView == null){
            return;
        }
        timer.setVisibility(View.INVISIBLE);
        inputUser.setVisibility(View.INVISIBLE);
        send.setVisibility(View.INVISIBLE);
        quit.setVisibility(View.VISIBLE);
        if (offlineGame != null){
            if (offlineGame.player1Turn){
                setOther("Player 2 won!");
            }
            else{
                setOther("Player 1 won");
            }
        }
        this.offlineGame = null;
    }


}
