package com.example.groupmanagment.fragments;

//description below
/*
* this fragment is added into the mainActivity
* it has the recyclerView that lists the list of friends that are added
* the reccyclerView use the adapter to link the data into it*/

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.groupmanagment.R;
import com.example.groupmanagment.adapters.FriendsListAdapter;
import com.example.groupmanagment.database.DBUtility;
import com.example.groupmanagment.modles.FriendContact;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsListFragment extends Fragment {

    private FriendsListAdapter friendsListAdapter;
    private ArrayList<FriendContact> friends = new ArrayList<>();
    private DBUtility dbUtility;

    private View view;
    private RecyclerView rv_friends;
    private LinearLayout layout_init; //init text, the first text that tells to add friends if there was no friends added

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends_list, container, false);

        dbUtility = new DBUtility(getContext());

        setUpViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkForFriendsList();
    }

    private void setUpViews(){
        layout_init = view.findViewById(R.id.layout_init);
        rv_friends = view.findViewById(R.id.rv_friends);

        rv_friends.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_friends.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void checkForFriendsList(){
        friends = dbUtility.getFriends();

        if(friends.size() == 0) {
            layout_init.setVisibility(View.VISIBLE);
            rv_friends.setVisibility(View.GONE);
        }else{
            layout_init.setVisibility(View.GONE);
            rv_friends.setVisibility(View.VISIBLE);

            friendsListAdapter = new FriendsListAdapter(friends, getContext());
            rv_friends.setAdapter(friendsListAdapter);
        }
    }


}
