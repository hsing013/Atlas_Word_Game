package com.example.harsh101.atlas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingScreen extends Fragment {

    public View myView = null;
    public Button returnButton = null;
    public TextView myBanner = null;

    public LoadingScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (myView == null){
            myView = inflater.inflate(R.layout.fragment_loading_screen, container, false);
            returnButton = myView.findViewById(R.id.menuButton);
            myBanner = myView.findViewById(R.id.textView);
            returnButton.setVisibility(View.INVISIBLE);
        }
        // Inflate the layout for this fragment
        defaultScreen();
        return myView;
    }

    public void defaultScreen(){
        myBanner.setText("Loading...");
        returnButton.setVisibility(View.INVISIBLE);
    }

    public void exposeMenu(){
        returnButton.setVisibility(View.VISIBLE);
    }

}
