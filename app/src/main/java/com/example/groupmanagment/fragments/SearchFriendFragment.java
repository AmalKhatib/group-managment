package com.example.groupmanagment.fragments;

//description below
/*
* this fragment is added when the user click on the search icon of the mainActivity
* after the search is submitted, it invokes the search method from the DBUtility
* to list the ound friend based on the search value*/

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanagment.R;
import com.example.groupmanagment.activities.FriendDetailsActivity;
import com.example.groupmanagment.activities.MainActivity;
import com.example.groupmanagment.database.DBUtility;
import com.example.groupmanagment.modles.FriendContact;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFriendFragment extends Fragment {

    private TextView tv_name ;
    private ImageView img_delete;
    private View view;

    private RelativeLayout rl_container ;

    private DBUtility dbUtility;
    private ArrayList<FriendContact> friends ;



    public SearchFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String searchValue = getArguments().getString("message");



        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_search_friend, container, false);

         dbUtility = new DBUtility(view.getContext());

         friends = dbUtility.searchByNameOrPhone(searchValue);

         findViews();

         clickListener();


        return view;
    }


    private void findViews(){
        tv_name = view.findViewById(R.id.tv_name);
        tv_name.setText(friends.get(0).getName());

        img_delete = view.findViewById(R.id.img_delete);

        rl_container = view.findViewById(R.id.rl_container);

    }


    private void clickListener(){
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(tv_name.getText().toString());
                rl_container.setVisibility(View.GONE);

                //to switch into fragment of whole friends after the searched friend is deleted
                MainActivity mainActivity = new MainActivity();
                mainActivity.addFragment(new FriendsListFragment());
            }
        });


        rl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , FriendDetailsActivity.class);
                intent.putExtra("friend", friends.get(0));
                getContext().startActivity(intent);
            }
        });
    }

    private void deleteItem(String name){
        DBUtility dbUtility = new DBUtility(getContext());

        long result = dbUtility.deleteFriend(name);

        if(result > 0){
            Toast.makeText(getContext(), "Friend deleted.", Toast.LENGTH_SHORT).show();

        }
    }






}
