package com.example.billzg.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todoappDB.db";
    private static final String create_table_users = "create table users (" +
            "_id integer primary key autoincrement," +
            "username text ," +
            "password text );";

    private static final String create_table_activities = "CREATE TABLE activites (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "uid INTEGER NOT NULL ," +
            "name text ," +
            "FOREIGN KEY (uid) REFERENCES users(_id) );";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table_users);
        db.execSQL(create_table_activities);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users ");
        db.execSQL("DROP TABLE IF EXISTS activites");
        onCreate(db);
    }


    public void createUser(User user){
        ContentValues values = new ContentValues();
        values.put("username", user.get_username());
        values.put("password", user.get_password());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, values);
        db.close();
    }


    //Strings user table
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM users WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("username")) != null) {
                dbString += " ID : ";
                dbString += recordSet.getInt(recordSet.getColumnIndex("_id"));
                dbString += " username : ";
                dbString += recordSet.getString(recordSet.getColumnIndex("username"));
                dbString += " password : ";
                dbString += recordSet.getString(recordSet.getColumnIndex("password"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        recordSet.close();
        return dbString;
    }


    //checks user for log in
    public Boolean checkUser(User user){
        String username = user.get_username();
        String password = user.get_password();

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[] {username, password});
        System.out.println("Cursor get count gives : " + c.getCount() );

        if (c.getCount() > 0 ){
            c.close();
            db.close();
            return true;
        }else{

            db.close();
            return false;
        }
    }


    //returns ID from name
    public int fetchIdFromName(String myName) {
        int id;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT _id from users where username = ?", new String[] {myName});
        c.moveToFirst();
        id = c.getInt(c.getColumnIndex("_id"));
        c.close();
    return id;
    }


    //handles add new activity in activities TABLE
    public void addActivity(Activities activity) {
    SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("uid", activity.get_uid());
        values.put("name", activity.get_name());
        db.insert("activites", null, values);
        db.close();
    }


    // this returns the String for Activites for a specific User id
    public String databaseToStringForActivities(int id) {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        //Cursor points to a location in your results
        Cursor recordSet;
        recordSet = db.rawQuery("select * from activites where uid = ?", new String[] {""+id});
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("name")) != null) {
                dbString += " UID : ";
                dbString += recordSet.getInt(recordSet.getColumnIndex("uid"));
                dbString += " Description : ";
                dbString += recordSet.getString(recordSet.getColumnIndex("name"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        recordSet.close();
        return dbString;
    }


    //this returns the arraylist for Activites for a specific User id
    public ArrayList<String> databaseToArraylistForActivities(int id) {
        ArrayList<String> myList = new ArrayList<String>();
        String dbString;
        SQLiteDatabase db = getWritableDatabase();
        //Cursor points to a location in your results
        Cursor recordSet;
        recordSet = db.rawQuery("select * from activites where uid = ?", new String[] {""+id});
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("name")) != null) {
                dbString = recordSet.getString(recordSet.getColumnIndex("name"));
                myList.add(dbString);
            }
            recordSet.moveToNext();
        }
        db.close();
        recordSet.close();
        if (!myList.isEmpty()){
            return myList;
        }
        else{
            //return something so that it's not null
            return myList;
        }
    }


    //we can also pass the itemId of each item clicked instead of the name of entry and usersId it would also work
    public void deleteThisItem(String currentLine, int itemId) {
        //debugging
        System.out.println("this is currentLine :"+currentLine);
        System.out.println("this is itemId :"+itemId);

        SQLiteDatabase db = getWritableDatabase();
/*
        Cursor recordSet;
        recordSet = db.rawQuery("select * from activites where name = ? and uid = ?", new String[] {currentLine, ""+itemId});
        //Move to the first row in your results
        recordSet.moveToFirst();
   */
        db.delete("activites","name = ? and uid = ?", new String[] {currentLine, ""+itemId});
        db.close();
    }
}
