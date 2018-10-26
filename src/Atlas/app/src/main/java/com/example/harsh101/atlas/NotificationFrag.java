package com.example.harsh101.atlas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFrag extends Fragment {


    public NotificationFrag() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);

        /**
         * BottomNavigationMenuView bottomNavigationMenuView =
         *             (BottomNavigationMenuView) navigationView.getChildAt(0);
         *     View v = bottomNavigationMenuView.getChildAt(3);
         *     BottomNavigationItemView itemView = (BottomNavigationItemView) v;
         *
         *     View badge = LayoutInflater.from(this)
         *             .inflate(R.layout.notification_badge, bottomNavigationMenuView, false);
         *
         *     itemView.addView(badge);
         */
        /
    }

}
