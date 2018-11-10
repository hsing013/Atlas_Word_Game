package com.example.harsh101.atlas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.harsh101.atlas.R.id.action_add_friend;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFrag extends Fragment {
    public View myView = null;
    public ListView listView = null;
    public FriendArrayAdapter adapter = null;
    public ArrayList<Friend> friends = null;
    public MainActivity main = null;

    public FriendFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (myView == null){
            myView = inflater.inflate(R.layout.fragment_friend, container, false);
            listView = myView.findViewById(R.id.friendListView);
            if (adapter == null || friends == null){
                System.exit(-1);
            }
            listView.setAdapter(adapter);
            setHasOptionsMenu(true);
        }

        return myView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.friendlist_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case action_add_friend:
                System.out.println("Add Friend");
                main.onAddFriend(null);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
    public void setList(ArrayList<Friend> newList){
        if (friends == null){
            friends = newList;
        }
        else{
            friends.clear();
            friends.addAll(newList);
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void addToList(Friend f){
        if (friends != null){
            friends.add(f);
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void signOut(){
        if (friends != null){
            friends.clear();
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }

}
