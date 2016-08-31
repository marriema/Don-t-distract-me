package com.example.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;



public class First extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
    }

    public void first_enter_pressed(View view) {
        // switch to register activity
        //Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_SHORT).show();
        EditText mEdit   = (EditText)findViewById(R.id.editText1);
        String str = mEdit.getText().toString();
        ((MyApplication)getApplication()).setUsername(str);
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}