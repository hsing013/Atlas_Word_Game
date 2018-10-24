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
public class SettingFrag extends Fragment {

    public ListView settingView = null;
    public View myView = null;
    public ArrayList<String> slist = null;
    public ArrayAdapter<String> sAdapter = null;


    public SettingFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(myView == null)
        {
            myView = inflater.inflate(R.layout.fragment_setting, container, false);
            settingView = myView.findViewById(R.id.settngView);
            if(slist == null)
            {
                slist = new ArrayList<>();
            }
            sAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, slist);
            settingView.setAdapter(sAdapter);
        }
        return myView;
    }

    public void fillList(ArrayList<String> newSlist)
    {
        if(slist == null)
        {
            slist = newSlist;
        }
        else
        {
            slist.clear();
            for(int i = 0; i < slist.size(); i++)
            {
                slist.add(newSlist.get(i));
            }
        }
        if(sAdapter != null) {
            sAdapter.notifyDataSetChanged();
        }
    }

    public void addTolist(String s) {
        if(s != null) {
            slist.add(s);
            sAdapter.notifyDataSetChanged();
        }
    }

}
