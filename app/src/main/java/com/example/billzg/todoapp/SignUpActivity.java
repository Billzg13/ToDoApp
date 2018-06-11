package com.example.billzg.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    private MyDBHandler DbHandler;
    private EditText textUserName;
    private EditText textPassword;
    private TextView productView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        textUserName = (EditText) findViewById(R.id.etUserName);
        textPassword = (EditText) findViewById(R.id.etPassword);
        DbHandler = new MyDBHandler(this, null, null , 1);
        productView = (TextView) findViewById(R.id.productText);
        printDatabase();
    }


    public void backButtonMethod(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void signUpMethod(View view) {
        User newUser = new User(textUserName.getText().toString().trim(), textPassword.getText().toString().trim());
        DbHandler.createUser(newUser);
        printDatabase();
    }

    public void printDatabase(){
        String dbString = DbHandler.databaseToString();
        productView.setText(dbString);
    }

}
