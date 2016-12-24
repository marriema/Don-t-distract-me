package com.example.androidtest;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.Editable;



/**
 * Created by Yanqing Ma on 11/1/2016.
 */

// application includes getters and setters
public class MyApplication extends Application {

    private String username;
    private int time;
    private DatabaseHelper db;
    private String ds;

    public String getUsername() {
        return username;
    }
    public int getTime(){
        return time;
    }

    public String getDS(){
        return ds;
    }

    public DatabaseHelper getDB(){
        return db;
    }

    public void setUsername(String someVariable) {this.username = someVariable;}
    public void setTime(int someVariable) {this.time = someVariable;}
    public void setDB(DatabaseHelper db) {this.db = db;}
    public void setDs(String ds){this.ds = ds;}




}