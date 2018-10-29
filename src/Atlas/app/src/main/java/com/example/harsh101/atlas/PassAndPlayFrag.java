//package com.example.harsh101.atlas;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//
//public class PassAndPlayFrag extends Fragment {
//    public View myView = null;
//    public TextView Player2 = null;
//    public TextView timer = null;
//    public EditText Player1 = null;
//    public Button sendWord = null;
//    public Button quit = null;
//    public Game currentGame = null;
//
//   public PassAndPlayFrag()
//   {
//
//   }
//
//    public View createView(LayoutInflater inflater, ViewGroup container, Bundle saveState)
//    {
//        if (myView == null)
//        {
//            //********************************************************
//            // Not sure what to do here. Was looking at the GameFrag file
//            // and I could not figure out what it was doing exactly
//            //********************************************************
//
//
//            //myView = inflater.inflate(R.layout.frag)
//            //Player2 = myView.findViewById(R.id.Player2);
//        }
//        return myView;
//    }
//
//    public void reset()
//    {
//        if (myView == null)
//        {
//            return;
//        }
//        Player2.setText("");
//        timer.setText("");
//        Player1.setEnabled(false);
//        sendWord.setEnabled(false);
//        timer.setVisibility(View.VISIBLE);
//        Player1.setVisibility(View.VISIBLE);
//        sendWord.setVisibility(View.VISIBLE);
//        quit.setVisibility(View.VISIBLE);
//    }
//
//    public String getInput()
//    {
//        if (myView == null)
//        {
//            return null;
//        }
//
//        sendWord.setEnabled(false);
//        String getWord = Player1.getText().toString();
//        Player1.setText("");
//        Player1.setEnabled(false);
//        return getWord;
//    }
//
//    public void setButton(boolean flag)
//    {
//        if (myView == null)
//        {
//            return;
//        }
//        sendWord.setEnabled(flag);
//    }
//
//    public void Player2Turn()
//    {
//        if (myView == null)
//        {
//            return;
//        }
//
//        Player1.setEnabled(false);
//        Player1.setText("");
//        sendWord.setEnabled(false);
//    }
//
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
//
//    public void setTimer(String sTimer)
//    {
//        if (myView == null){
//            return;
//        }
//        timer.setText(sTimer);
//    }
//
//    public void setPlayer2(String p2)
//    {
//        if (myView == null)
//        {
//            return;
//        }
//
//        Player2.setText(p2);
//    }
//
//    public void endGame()
//    {
//        if (myView == null){
//            return;
//        }
//        timer.setVisibility(View.INVISIBLE);
//        Player1.setVisibility(View.INVISIBLE);
//        sendWord.setVisibility(View.INVISIBLE);
//        quit.setVisibility(View.VISIBLE);
//    }
//
//    public void staleMate(){
//        if (myView == null){
//            return;
//        }
//        Player1.setEnabled(false);
//        Player1.setText("");
//        timer.setText("");
//        sendWord.setEnabled(false);
//    }

