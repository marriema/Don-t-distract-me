package com.example.androidtest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.*;
import java.io.IOException;
import java.lang.*;


/**
 * Created by Yanqing Ma on 11/6/2016.
 */
public class Sign extends Activity{

    DatabaseHelper myDB;

    @Override
    // connect to view and start database
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);
        myDB = ((MyApplication)getApplication()).getDB();

    }


    //check if database has this name, if yes, pop up a window. Otherwise, just go to next page
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void btn_confirm_pressed(View view) throws IOException {
        // switch to register activity
        //Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_SHORT).show();
        EditText name   = (EditText)findViewById(R.id.editText2);
        EditText password = (EditText)findViewById(R.id.editText3);
        String str_name = name.getText().toString();
        String str_password = password.getText().toString();

        if(str_password.isEmpty() || str_name.isEmpty())
            Toast.makeText(getApplicationContext(), "Empty name or password", Toast.LENGTH_SHORT).show();


        else if(!myDB.isNameExisted(str_name))
        {
            ((MyApplication) getApplication()).setUsername(str_name);
            myDB.insertEntry(str_name,str_password);
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "duplicate username", Toast.LENGTH_SHORT).show();
        }
    }





}