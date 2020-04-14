package com.example.groupmanagment.adapters;

// adapter description below
/*
* adapter class mission is to adapt the list of rows that comes from the DB into the recycler view
* it takes the ararylist of the DB rows and the context which is the current activity, as an attributes in the constructor
* it has a custom view(int the view holder) that has a template for displaying the data of each row in the list
* it assigns the data into this custom view for each row fro the DB
* */
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanagment.R;
import com.example.groupmanagment.activities.FriendDetailsActivity;
import com.example.groupmanagment.database.DBUtility;
import com.example.groupmanagment.modles.FriendContact;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder>  {

    private ViewHolder viewHolder;

    private ArrayList<FriendContact> friends;
    private Context context;

    public FriendsListAdapter(ArrayList<FriendContact> friends, Context context) {
        this.friends = friends;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_items_list, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull FriendsListAdapter.ViewHolder holder, int position) {
        viewHolder.tv_name.setText(friends.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        TextView tv_name;
        ImageView btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            tv_name = rootView.findViewById(R.id.tv_name);
            btn_delete = rootView.findViewById(R.id.img_delete);

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(friends.size() != 0)
                        friends.remove(getAdapterPosition());
                    deleteItem(tv_name.getText().toString());
                }
            });


            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context , FriendDetailsActivity.class);
                    intent.putExtra("friend", friends.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

        }

    }

    private void deleteItem(String name){
        DBUtility dbUtility = new DBUtility(context);

        long result = dbUtility.deleteFriend(name);

        if(result > 0){
            Toast.makeText(context, "Friend deleted.", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        }
    }
}
