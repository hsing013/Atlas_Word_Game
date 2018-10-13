package com.example.harsh101.atlas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFrag extends Fragment {
    public View myView = null;
    public ListView leaderboardListView = null;
    public ArrayAdapter<String> adapter = null;
    public ArrayList<String> list = null;


    public LeaderboardFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(myView == null) {
            myView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
            leaderboardListView = myView.findViewById(R.id.leaderboardListView);
            if(list == null) {
                list = new ArrayList<>();
            }
            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
            leaderboardListView.setAdapter(adapter);
        }
        return myView;
    }

    public void setList(ArrayList<String> newList) {
        list = newList;
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void addToList(String s) {
        if(s != null) {
            list.add(s);
            adapter.notifyDataSetChanged();
        }
    }

}
