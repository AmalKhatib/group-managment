package com.example.groupmanagment.activities;

//activity description below
/*
 * this activity is main display after the splash
 * it is used for displaying the whole set of friends, by adding "FriendsListFragment" into it
 * and for displaying the value of search, byt "SearchFriendFragment"
 * */

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.groupmanagment.R;
import com.example.groupmanagment.database.DBUtility;
import com.example.groupmanagment.fragments.FriendsListFragment;
import com.example.groupmanagment.fragments.SearchFriendFragment;
import com.example.groupmanagment.modles.FriendContact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    FriendsListFragment fragment = new FriendsListFragment();
    private DBUtility dbUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddFriendActivity.class);
                startActivity(intent);
            }
        });

        setUpViews();
        addFragment(fragment);

        dbUtility = new DBUtility(this);
    }

    private void setUpViews() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchmenuItem = menu.findItem(R.id.menu_toolbarsearch);
        SearchView searchView = (SearchView) mSearchmenuItem.getActionView();
        searchView.setQueryHint("name or mobile number");
        searchView.setOnQueryTextListener(this);

        //when the user click on search, the Search fragment is invoked
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
            addFragment(fragment);
            return false;
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Bundle bundle=new Bundle();
        bundle.putString("message", query);

        SearchFriendFragment searchFragment = new SearchFriendFragment();
        searchFragment.setArguments(bundle);
        //move to search fragment to display the result
        addFragment(searchFragment);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() == 0) {
            addFragment(fragment);
        }

        return true;
    }

    public void addFragment(Fragment frag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, frag);
        fragmentTransaction.commit();
    }
}
