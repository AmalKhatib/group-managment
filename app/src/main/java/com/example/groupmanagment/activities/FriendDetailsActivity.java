package com.example.groupmanagment.activities;

//activity description below
/*
 this activity for displaying the chosen friend’s details & also for updating its info
 * the user will not be able to update at first because the EditText fields are disable
 * they wll be converted to enabled and the user can edt them, when he presses on the "edt" icon on the top of the toolbar
 * when the user is done of editing he can press on update button to update the info by "update" methon on the DBUtiity object
 * */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmanagment.R;
import com.example.groupmanagment.database.DBUtility;
import com.example.groupmanagment.modles.FriendContact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FriendDetailsActivity extends AppCompatActivity {

    DBUtility dbUtility;

    private TextView tv_name , tv_email , tv_mobile_number ;
    private FriendContact friendContact ;
    private ImageView ic_edit ;
    private Button btn_update;

    String oldName; //to save the value of the old name to use it in update query

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ic_edit = toolbar.findViewById(R.id.ic_edit);

        friendContact = (FriendContact) getIntent().getSerializableExtra("friend");
        dbUtility = new DBUtility(this);

        findViews();
        setUpViews();
        clickListener();

    }

    private void findViews(){
        tv_email = findViewById(R.id.tv_email);
        tv_name = findViewById(R.id.tv_name);
        tv_mobile_number = findViewById(R.id.tv_mobile_number);
        btn_update = findViewById(R.id.btn_delete);
    }

    private void setUpViews(){
        tv_name.setText(friendContact.getName());
        tv_email.setText(friendContact.getEmail());
        tv_mobile_number.setText(friendContact.getMobileNumber());

        oldName = tv_name.getText().toString();
    }

    private void clickListener(){
        ic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_update.setVisibility(View.VISIBLE);
                tv_email.setEnabled(true);
                tv_name.setEnabled(true);
                tv_mobile_number.setEnabled(true);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long result = dbUtility.updateFriend(
                        oldName,
                        tv_name.getText().toString(),
                        tv_email.getText().toString(),
                        tv_mobile_number.getText().toString());

                if(result > 0){
                    Toast.makeText(FriendDetailsActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(FriendDetailsActivity.this, "error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
