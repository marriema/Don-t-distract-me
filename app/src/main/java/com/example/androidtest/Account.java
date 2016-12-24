package com.example.androidtest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;


/**
 * Created by marriema on 11/27/16.
 */
public class Account extends Activity {


    String username;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView name;
    Button but;
    DatabaseHelper dbhelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewaccount);
        dbhelper = ((MyApplication) getApplication()).getDB();
        username = ((MyApplication) getApplication()).getUsername();

        name = (TextView) findViewById(R.id.textView5);
        name.setText("Hello! " + username);

        t1 = (TextView) findViewById(R.id.editText4);
        t2 = (TextView) findViewById(R.id.editText5);
        t3 = (TextView) findViewById(R.id.editText6);
        t4 = (TextView) findViewById(R.id.editText7);
        but = (Button) findViewById(R.id.button4);


        t1.setText("total study time: " + dbhelper.getStat(username, "total") + " min");
        int fre = dbhelper.getStat(username, "frequency");
        double average = 0;
        if(fre != 0)
             average = dbhelper.getStat(username, "total")/fre;

        t3.setText("average study time: " + average + " min"
        );
        t2.setText("longest time for study:  "+ longestStudyTime(username) +" min");
        int r = getUserRank(username);
        t4.setText("rank against other users: " + getUserRank(username));
        if(r <= 3)
            t4.setTextColor(Color.parseColor("#ff0000"));


        try {
            testGetUserRank();
            testLongestStudyTime();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    /**
     * helper function for finding longest study time for a user from record table
     *
     * @param  username  username
     * @return int longest time
     */
    public int longestStudyTime(String username)
    {
        if(username != null)
        {
            List<Integer> ret = dbhelper.getStudyTimeList(username);
            if(ret.size() == 0)
                return 0;
            Collections.sort(ret, Collections.reverseOrder());
            return ret.get(0);

        }
        return 0;
    }



    /**
     * helper function for finding the rank of study frequency for one user
     *
     * @param  username  username
     * @return int rank
     */
    public int getUserRank(String username)
    {
        int ret = 0;
        if(username != null)
        {
            ret = dbhelper.getRank(username);

        }
        return ret + 1;
    }




    /**
     * button trigger for "plot graph" view
     *
     * @param  view  current view
     */
    public void btnPressed(View view) {
        Intent intent1 = new Intent(this, Graph.class);
        startActivity(intent1);
    }




    /**
     * testcase for getUserRank()
     *
     * test the frequency rank for each user
     */
    public void testGetUserRank() throws Exception {
        int r = getUserRank("marrie");
        assertEquals(r, 1);
        r = getUserRank("trump");
        assertEquals(r, 2);
    }



    /**
     * testcase for longestStudyTime()
     *
     * test the longest study time for a user
     */
    public void testLongestStudyTime() throws Exception {
        int most = longestStudyTime("marrie");
        assertEquals(most, 5);
        most = longestStudyTime("trump");
        assertEquals(most, 3);
    }
}
