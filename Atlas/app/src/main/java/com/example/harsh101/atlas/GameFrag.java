package com.example.harsh101.atlas;


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
public class GameFrag extends Fragment {

    public View myView = null;
    public TextView otherPlayer = null;
    public TextView timer = null;
    public EditText inputUser = null;
    public Button send = null;
    public Button quit = null;
    public Game currentGame = null;


    public GameFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (myView == null){
            myView = inflater.inflate(R.layout.fragment_game, container, false);
            otherPlayer = myView.findViewById(R.id.otherPlayer);
            timer = myView.findViewById(R.id.countDown);
            inputUser = myView.findViewById(R.id.wordInput);
            send = myView.findViewById(R.id.send);
            quit = myView.findViewById(R.id.quit);
            quit.setVisibility(View.INVISIBLE);
            System.out.println("i am here");
            if (currentGame != null){
                System.out.println("yello");
                if (currentGame.myTurn){
                    setOther("Your word: " + currentGame.myWord);
                    myTurn();
                }
                else{
                    setOther("Other player' turn");
                    otherPlayerTurn();
                }
            }
        }

        // Inflate the layout for this fragment
        return myView;
    }



    public void reset(){
        if (myView == null){
            return;
        }
        otherPlayer.setText("");
        timer.setText("");
        inputUser.setEnabled(false);
        inputUser.setText("");
        send.setEnabled(false);
        timer.setVisibility(View.VISIBLE);
        inputUser.setVisibility(View.VISIBLE);
        send.setVisibility(View.VISIBLE);
        quit.setVisibility(View.INVISIBLE);
    }

    public String getInput(){
        if (myView == null){
            return null;
        }
        send.setEnabled(false);
        String word = inputUser.getText().toString();
        inputUser.setText("");
        inputUser.setEnabled(false);
        return word;
    }

    public void setButton(boolean flag){
        if (myView == null){
            return;
        }
        send.setEnabled(flag);
    }

    public void setInputUser(boolean flag){
        if (myView == null){
            return;
        }
        inputUser.setEnabled(flag);
    }

    public void otherPlayerTurn(){
        if (myView == null){
            return;
        }
        inputUser.setEnabled(false);
        inputUser.setText("");
        send.setEnabled(false);
    }

    public void myTurn(){
        if (myView == null){
            return;
        }
        inputUser.setEnabled(true);
        inputUser.setText("");
        send.setEnabled(true);
    }

    public void setTimer(String s){
        if (myView == null){
            return;
        }
        timer.setText(s);
    }

    public void setOther(String s){
        if (myView == null){
            return;
        }
        otherPlayer.setText(s);
    }

    public void endGame(){
        if (myView == null){
            return;
        }
        timer.setVisibility(View.INVISIBLE);
        inputUser.setVisibility(View.INVISIBLE);
        send.setVisibility(View.INVISIBLE);
        quit.setVisibility(View.VISIBLE);
    }

    public void staleMate(){
        if (myView == null){
            return;
        }
        inputUser.setEnabled(false);
        inputUser.setText("");
        timer.setText("");
        send.setEnabled(false);
        otherPlayer.setText("Waiting for data from server.");
    }



}
