package com.example.androidtest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.*;
import java.io.IOException;


/**
 * Created by Yanqing Ma on 11/1/2016.
 */
public class First extends Activity {


    DatabaseHelper dbhelper;


    @Override
    // connect to view and start database
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        this.dbCreation();
        this.testGetList();
        this.testIsNameExisted();
        try {
            this.testInsertRecord();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.testIfExists();


    }


    // initialize a database and copy from disk
    public void dbCreation() {
        dbhelper = new DatabaseHelper(this);
        try {

            dbhelper.createDataBase();
            ((MyApplication) getApplication()).setDB(this.dbhelper);

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

    }


    // log in button function, check input is empty or not and call database to check valid name and password
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void btnLogInPressed(View view) {
        EditText username = (EditText) findViewById(R.id.editText);
        EditText password = (EditText) findViewById(R.id.editText1);
        String str_name = username.getText().toString();
        String str_password = password.getText().toString();

        if (str_password.isEmpty() || str_name.isEmpty())
            Toast.makeText(getApplicationContext(), "Empty name or password", Toast.LENGTH_SHORT).show();
        else if (dbhelper.ifExists(str_name, str_password)) {
            ((MyApplication) getApplication()).setUsername(str_name);
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(), "invalid name or password", Toast.LENGTH_SHORT).show();

    }


    // sign up button go to sign up page
    public void btnSignUpPressed(View view) {
        Intent intent = new Intent(this, Sign.class);
        startActivity(intent);
    }



    // test case for testing getUsernameList()
    public void testGetList() {
        List<String> ret = dbhelper.getList();
        boolean isSizedCorrect = (ret.size() == 11);
        List<String> ans = Arrays.asList("marrie", "bob", "lala", "bhg", "js", "shhhhs", "khg", "d", "da", "shicheng", "trump");
        Collections.sort(ret);
        Collections.sort(ans);
        Boolean isItemCorrect = ret.equals(ans);
        Log.d("testGetList: ", "isSizeCorrect " + isSizedCorrect + "\n");
        Log.d("testGetList: ", "isItemCorrect " + isItemCorrect + "\n");

    }


    // test case for testing isNameExisted()
    public void testIsNameExisted() {
        Log.d("testIsNameExisted1 ", dbhelper.isNameExisted("marrie") + "\n");
        Log.d("testIsNameExisted2 ", dbhelper.isNameExisted("tty") + "\n");

    }


    // test case for testing insertingRecord()
    public void testInsertRecord() throws IOException {
        dbhelper.insertEntry("trump", "president");
        boolean ans = (dbhelper.getList().size() == 11);
        Log.d("testInsertRecord ", ans + "\n");
    }


    // test case for testing isExists()
    public void testIfExists()  {
        Log.d("testisExisted1 ", dbhelper.ifExists("marrie", "abc") + "\n");
        Log.d("testisExisted2 ", dbhelper.ifExists("hillary", "president") + "\n");
    }


}
