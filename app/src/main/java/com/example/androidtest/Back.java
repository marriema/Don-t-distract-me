package com.example.androidtest;

/**
 * Created by marriema on 11/4/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;



public class Back extends Activity {
    Button goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fail);
        DatabaseHelper dbhelper = ((MyApplication) getApplication()).getDB();
        String username = ((MyApplication) getApplication()).getUsername();
        String com = ((MyApplication) getApplication()).getDS();
        int time = ((MyApplication) getApplication()).getTime();
        dbhelper.ifRecordExist(username, com, time, 1);


        goback = (Button) findViewById(R.id.button2);

        goback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(Back.this, First.class);
                startActivity(intent);

            }
        });
    }



}


