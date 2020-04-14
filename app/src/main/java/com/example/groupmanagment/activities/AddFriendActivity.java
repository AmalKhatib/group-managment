package com.example.groupmanagment.activities;

//activity description below
/*
* this activity for adding a new friend to the SQLite DB
* 0t takes the values that the user enters in the EditText fields
* and assign it into the method of insert form the DBUtility object that is created
* */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupmanagment.R;
import com.example.groupmanagment.database.DBUtility;
import com.example.groupmanagment.modles.FriendContact;

public class AddFriendActivity extends AppCompatActivity {

    private DBUtility dbUtility;

    private EditText et_name, et_email, et_phone;
    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        dbUtility = new DBUtility(this);

        setUpViews();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
    }

    public void setUpViews(){
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        btn_add = findViewById(R.id.btn_add);
    }

    public void addFriend(){
        if(et_name.getText().toString().equals("") ||
                et_email.getText().toString().equals("") ||
                et_phone.getText().toString().equals("")){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else{
            FriendContact friendContact = new FriendContact(
                    et_name.getText().toString(),
                    et_email.getText().toString(),
                    et_phone.getText().toString()
            );

            long result = dbUtility.insertContact(friendContact);

            if(result < 0)
                Toast.makeText(this, "Duplicated info!", Toast.LENGTH_SHORT).show();
            else{
                Toast.makeText(this, "Successfully added.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
