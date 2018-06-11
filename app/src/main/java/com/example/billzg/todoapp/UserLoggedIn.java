package com.example.billzg.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserLoggedIn extends AppCompatActivity {

    private TextView txt;
    private TextView txt3;
    private String myName;
    private  int myId;
    private MyDBHandler dbHandler;
    private ArrayList<String> thingsList;
    private ListView myList;
    private CustomArrayAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_in);

        thingsList = new ArrayList();
        txt = (TextView) findViewById(R.id.textView);
        txt3 = (TextView) findViewById(R.id.textView4);
        dbHandler = new MyDBHandler(this, null, null, 1);
        myList = (ListView) findViewById(R.id.myListView);

        Bundle name = getIntent().getExtras();
        myName = name.getString("username");

        myId = dbHandler.fetchIdFromName(myName);

        txt.setText("HELLO : "+myName +" your ID is "+myId);

        thingsList = dbHandler.databaseToArraylistForActivities(myId);
        System.out.println("things list has? "+thingsList);
        if (!thingsList.isEmpty()) {
            try {
                txt3.setText("size of arrayList is : " + thingsList.size());
            } catch (Exception e) {
                System.out.println("Exception happened cause no things to show");
            }
        }else{
            txt3.setText("add or refresh");
        }

        myAdapter = new CustomArrayAdapter(this, 1, thingsList, myId);
        myList.setAdapter(myAdapter);
    }

    public void addNewThing(View view){
        Intent i = new Intent(this, AddNewThing.class);
        i.putExtra("username", myName);
        startActivity(i);
    }

    public void Refresh(View view){

        thingsList = dbHandler.databaseToArraylistForActivities(myId);
        try {
            txt3.setText("size of arrayList is : " + thingsList.size());
            System.out.println("HELLO FROM REFRESH");
            System.out.println("things list has : "+thingsList);

            myAdapter = new CustomArrayAdapter(this, 1, thingsList, myId);
            myList.setAdapter(myAdapter);


        }catch (Exception e){
            System.out.println("Exception happened cause no things to show");
        }
    }


}
