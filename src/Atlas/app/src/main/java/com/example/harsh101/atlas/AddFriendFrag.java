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
public class AddFriendFrag extends Fragment {
    public View myView = null;
    public Button backButton = null;
    public Button searchButton = null;
    public TextView banner = null;
    public EditText input = null;

    public AddFriendFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (myView == null){
            myView = inflater.inflate(R.layout.fragment_add_friend, container, false);
            backButton = myView.findViewById(R.id.tempBack);
            searchButton = myView.findViewById(R.id.searchButton);
            banner = myView.findViewById(R.id.friendInfo);
            input = myView.findViewById(R.id.searchUser);
        }
        this.reset();
        return myView;
    }

    public void reset(){
        if (myView == null){
            return;
        }

        input.setText("");
        banner.setText("");
        searchButton.setEnabled(true);
    }

    public void setBanner(String text, boolean flag, String textButton){
        if (banner == null){
            return;
        }
        else{
            banner.setText(text);
            searchButton.setEnabled(true);
        }
    }

}
