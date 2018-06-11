package com.example.billzg.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewThing extends AppCompatActivity {

    int myId;
    String myName;
    MyDBHandler dbHandler;
    TextView testing ;
    EditText myText;
    TextView myText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_thing);

        myText = (EditText) findViewById(R.id.etToDo);
        testing = (TextView) findViewById(R.id.test);
        dbHandler = new MyDBHandler(this, null, null, 1);
        Bundle name = getIntent().getExtras();
        myName = name.getString("username");
        myId = dbHandler.fetchIdFromName(myName);
        myText2 = (TextView) findViewById(R.id.textView2);

        testing.setText("YOUR NAME IS "+myName +" your ID is : "+myId);
        myText2.setText(dbHandler.databaseToStringForActivities(myId));

    }

    public void addButtonMethod(View view){
        //fetch EditText and create new thing
        if (!myText.getText().equals("")){
            Activities activity = new Activities(myId, myText.getText().toString());
            dbHandler.addActivity(activity);
        }
        Toast tost = Toast.makeText(this, "activity added", Toast.LENGTH_SHORT);
        tost.show();
        myText2.setText(dbHandler.databaseToStringForActivities(myId));

    }

}
