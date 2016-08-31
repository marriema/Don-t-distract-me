package com.example.androidtest;
import android.app.Application;
import android.text.Editable;

/**
 * Created by szhou42 on 15/11/6.
 */
public class MyApplication extends Application {

    private String username;
    private int time;

    public String getUsername() {
        return username;
    }
    public int getTime(){
        return time;
    }

    public void setUsername(String someVariable) {this.username = someVariable;}
    public void setTime(int someVariable) {this.time = someVariable;}
}