package com.example.harsh101.atlas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFrag extends Fragment {


    public View myView = null;


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

        }
        return myView;
    }


}
