package com.example.harsh101.atlas;
/*


 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PassAndPassFrag extends Fragment {

    public View myView = null;
    public TextView Player2 = null;  //this is just the banner
    public TextView timer = null;
    public EditText Player1 = null; //input for both players
    public Button sendWord = null;
    public Button quit = null;
    public PassAndPlay currentGame = null;

    public PassAndPassFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (myView == null)
        {
            myView =  inflater.inflate(R.layout.fragment_game, container, false);
            Player2 = myView.findViewById(R.id.otherPlayer);
            timer = myView.findViewById(R.id.countDown);
            Player1 = myView.findViewById(R.id.wordInput);
            sendWord = myView.findViewById(R.id.send);
            quit = myView.findViewById(R.id.quit);
            if (currentGame != null)
            {
                if (currentGame.myTurn)
                {
                    setPlayer("Player1's Word: " + currentGame.myWord);
                    //Player1Turn();
                }

                else
                {
                    setPlayer("Player2's Word: " + currentGame.myWord);
                    //Player2Turn();
                }
            }
        }
        return myView;
    }

    public void reset()
    {
        if (myView == null)
        {
            return;
        }
        Player2.setText("");
        timer.setText("");
        Player1.setEnabled(false);
        sendWord.setEnabled(false);
        timer.setVisibility(View.VISIBLE);
        Player1.setVisibility(View.VISIBLE);
        sendWord.setVisibility(View.VISIBLE);
        quit.setVisibility(View.VISIBLE);
    }

    public String getInput()
    {
        if (myView == null)
        {
            return null;
        }

        sendWord.setEnabled(false);
        String getWord = Player1.getText().toString();
        Player1.setText("");
        Player1.setEnabled(false);
        return getWord;
    }

    public void setButton(boolean flag)
    {
        if (myView == null)
        {
            return;
        }
        sendWord.setEnabled(flag);
    }

    public void PlayerTurn()
    {
        if (myView == null || currentGame == null)
        {
            return;
        }

        if (currentGame.myTurn)
        {

            currentGame.myTurn = false;
        }

        else
        {
            currentGame.myTurn = true;
        }
    }

//    public void Player1Turn()
//    {
//        if (myView == null)
//        {
//            return;
//        }
//
//        Player2.setEnabled(false);
//        Player2.setText("");
//        sendWord.setEnabled(true);
//    }

    public void setTimer(String sTimer)
    {
        if (myView == null){
            return;
        }
        timer.setText(sTimer);
    }

    public void setPlayer(String p)
    {
        if (myView == null)
        {
            return;
        }

        Player2.setText(p);
    }

    public void endGame()
    {
        if (myView == null || currentGame == null){
            return;
        }
        if (currentGame.myTurn){
            Player2.setText("Player 2 won!");
        }
        else{
            Player2.setText("Player 1 won!");
        }
        timer.setVisibility(View.INVISIBLE);
        Player1.setVisibility(View.INVISIBLE);
        sendWord.setVisibility(View.INVISIBLE);
        quit.setVisibility(View.VISIBLE);
    }

    public void staleMate(){
        if (myView == null){
            return;
        }
        Player1.setEnabled(false);
        Player1.setText("");
        timer.setText("");
        sendWord.setEnabled(false);
    }

}
