package com.example.harsh101.atlas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 */
/*public class HowToPlayFrag extends Fragment {
    public View myView = null;
    public Button backButton = null;
    public HashSet<String> ruleTable = null;


    public HowToPlayFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (myView == null){
            myView = inflater.inflate(R.layout.fragment_how_to_play, container, false);

            InputStream is = getContext().openFileInput("howToPlayRules.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            String entireFile = "";
            try {
                while((line = br.readLine()) != null) { // <--------- place readLine() inside loop
                    entireFile += (line + "\n"); // <---------- add each line to entireFile

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            TextView text = myView.findViewById(R.id.textView4);
            text.setText(entireFile);
            //text.setText(entireFile); // <------- assign entireFile to TextView
            //assert text != null;
            if (text != null) {
                text.setText(entireFile);
            }


            backButton = myView.findViewById(R.id.backButtonSetting);

        }
        return myView;
    }



}*/
