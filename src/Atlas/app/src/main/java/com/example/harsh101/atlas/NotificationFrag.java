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
public class NotificationFrag extends Fragment {
    public View myView = null;
    public ListView listView = null;
    public ArrayList<Notification> notificationList = null;
    public ArrayAdapter adapter = null;
    public MainActivity main = null;
    public int notificationAmount = 0;

    public NotificationFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (myView == null){
            myView = inflater.inflate(R.layout.fragment_notification, container, false);
            listView = myView.findViewById(R.id.notificationListView);
            if (notificationList == null || adapter == null){
                System.exit(-1);
            }
            listView.setAdapter(adapter);
        }
        main.clearAndHideBadge();
        notificationAmount = 0;
        return myView;
    }

    public void addToList(Notification n){
        System.out.println("ADD TO LIST");
        if (notificationList != null){
            System.out.println("-----I AM IN-----");
            boolean check = false;
            for (int i = 0; i < notificationList.size(); ++i){
                Notification current = notificationList.get(i);
                if (n.type.equals(current.type) && n.from.equals(current.from)){
                    check = true;
                    break;
                }
            }

            if(!check){
                notificationList.add(n);
                adapter.notifyDataSetChanged();
            }
            System.out.print("I am here");
            if (!this.isVisible()){
                System.out.println("Triggered is visible");
                notificationAmount += 1;
                main.setFullBadge(notificationAmount);
            }
            else{
                System.out.println("Not triggered\n");
            }
        }
    }

    public Notification getAndRemove(int pos){
        if (notificationList != null && pos < notificationList.size()){
            Notification n = notificationList.get(pos);
            notificationList.remove(pos);
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
            return n;
        }

        return null;
    }

    public void setList(ArrayList<Notification> newList){
        if (notificationList == null){
            notificationList = newList;
        }
        else{
            notificationList.clear();
            notificationList.addAll(newList);
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void signOut(){
        if (notificationList != null){
            notificationList.clear();
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }

}
