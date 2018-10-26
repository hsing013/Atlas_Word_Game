package com.example.harsh101.atlas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFrag extends Fragment {
    public View myView = null;
    public ListView leaderboardListView = null;
    public LeaderboardAdapter adapter = null;
    public ArrayList<LeaderboardUser> list = null;


    public LeaderboardFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(myView == null) {
            myView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

            if (list == null || adapter == null){
                System.exit(-1);
            }

            leaderboardListView = myView.findViewById(R.id.leaderboardListView);
        // Uses custom LeaderBoardAdapter to show more than just a TextView for the Leaderboard
            leaderboardListView.setAdapter(adapter);
            System.out.println("this worked out pretty good.");
        }
        return myView;
    }

    public void setList(ArrayList<LeaderboardUser> newList) {
        if (list == null){
            list = newList;
        }
        else{
            list.clear();
            list.addAll(newList);
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void addToList(LeaderboardUser l) {
        if(l != null) {
            list.add(l);
            adapter.notifyDataSetChanged();
        }
    }

    public void signOut(){
        if (list != null){
            list.clear();
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }

}
