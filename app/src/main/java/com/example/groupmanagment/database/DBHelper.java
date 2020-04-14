package com.example.groupmanagment.database;

// adapter description below
/*
* this class initiate the database and declare the table with its columns(attributes)
* all columns are unique
* */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static String dp_name = "frin";
    private static int dp_version = 1;


        DBHelper(Context context) {
            super(context, dp_name, null, dp_version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table friends" +
                    "(name text unique, email text unique, phone text unique)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table friends");

            sqLiteDatabase.execSQL("create table cart" +
                    "(name text unique, email text unique, phone text unique)");
        }
    }



