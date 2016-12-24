package com.example.androidtest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.*;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import android.support.v7.app.AppCompatActivity;
import com.androidplot.series.*;
import com.androidplot.xy.XYPlot;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.*;
import android.graphics.*;


/**
 * Created by marriema on 11/28/16.
 */
public class Graph extends Activity {

    DatabaseHelper dbhelper;
    LineGraphSeries<DataPoint> series;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);

        dbhelper = ((MyApplication) getApplication()).getDB();
        String username = ((MyApplication) getApplication()).getUsername();

        List<Integer> list = dbhelper.getStudyTimeList(username);
        int size = list.size();

        int x, y;


        XYPlot mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        Number[] series1Numbers = {list.get(0), list.get(1),list.get(2)};

        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");

        series = new LineGraphSeries<DataPoint>();
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                Color.rgb(150, 190, 150));
        mySimpleXYPlot.addSeries(series1, series1Format);


    }


}



