package com.example.billzg.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPassword);
        dbHandler = new MyDBHandler(this, null, null , 1);
    }



    //handles sign up button click
    public void SignUpMethod(View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }


    //handles sign in button click
    public void SignInMethod(View view) {
        User user = new User();

        user.set_username(userName.getText().toString());
        user.set_password(password.getText().toString());

        String username = userName.getText().toString();

        if (!(user.get_username().isEmpty() || user.get_password().isEmpty() )){
            System.out.println("works!! if nothing is empty");

            if (checkUser(user)){
                //start new indent with user data
                Intent i = new Intent(this, UserLoggedIn.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        }

    }


    public Boolean checkUser(User user){
        //if user.getPass and user.getUserName is in DB go return true
        if (dbHandler.checkUser(user)){
            return true;
        }else{
            return false;
        }

    }

}
