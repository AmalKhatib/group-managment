package com.example.groupmanagment.database;

// adapter description below
/*
* this class has all the processes and methods that will be  done on he DB
* operations are, insert, delete, update, and get(select)
* cursor acts as a pointer in getting the ows from the db*/
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.groupmanagment.modles.FriendContact;

import java.util.ArrayList;

public class DBUtility {

    private final String DATABASE_TABLE = "friends";

    private DBHelper dbHelper;
    private Context context;

    public DBUtility(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public long insertContact(FriendContact mealInterface) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", mealInterface.getName());
        contentValues.put("email", mealInterface.getEmail());
        contentValues.put("phone", mealInterface.getMobileNumber());

        result = db.insert("friends", null, contentValues);

        return result;
    }

    public ArrayList<FriendContact> getFriends(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<FriendContact> friends = new ArrayList<>();
        FriendContact friendContact = null;

        Cursor cursor = db.query("friends", null, null, null, null,
                null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));

            friendContact = new FriendContact(name,email, phone);

            friends.add(friendContact);

            cursor.moveToNext();
        }
        cursor.close();
        return friends;
    }

    public long deleteFriend(String name){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(DATABASE_TABLE, "name" + "=?", new String[]{name});
    }

    public long updateFriend(String oldName , String newName , String newEmail , String newPhone){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", newName); //These Fields should be your String values of actual column names
        cv.put("email", newEmail);
        cv.put("phone", newPhone);

        return db.update(DATABASE_TABLE, cv, "name = ?", new String[]{oldName});
    }

    public ArrayList<FriendContact> searchByNameOrPhone(String query){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<FriendContact> friends = new ArrayList<>();
        FriendContact friendContact = null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ DATABASE_TABLE+" WHERE name = ? or phone = ?", new String[] {query , query});


        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));

            friendContact = new FriendContact(name,email, phone);

            friends.add(friendContact);

            cursor.moveToNext();
        }
        cursor.close();
        return friends;

    }
}
