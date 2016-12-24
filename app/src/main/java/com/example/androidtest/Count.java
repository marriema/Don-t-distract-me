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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Count extends Activity{


    private CountDownTimer timer;
    private String x;
    private int y;
    private ArrayList<String> arr_str;
    Button back;
    TextView time;
    ImageView i1;
    ImageView i2;
    ImageView i3;
    ImageView i4;
    DatabaseHelper dbhelper;

    private static final String FORMAT = "%02d:%02d:%02d";



    // start countdown clock and change the time every minute. When finished, return to register page
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        x = ((MyApplication)getApplication()).getUsername();
        y = ((MyApplication)getApplication()).getTime();
        dbhelper = ((MyApplication) getApplication()).getDB();

        time = (TextView) findViewById(R.id.textView);
        back = (Button) findViewById(R.id.button);
        i1 = (ImageView)findViewById(R.id.imageView2);
        i2 = (ImageView)findViewById(R.id.imageView4);
        i3 = (ImageView)findViewById(R.id.imageView5);
        i4 = (ImageView)findViewById(R.id.imageView);


        arr_str = new ArrayList<String>(5);

        startService(new Intent(this, ActivityList.class));

        timer = new CountDownTimer(y * 60 * 1000, 1000) {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onTick(long l) {
                time.setText(""+String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));

                if(TimeUnit.MILLISECONDS.toMinutes(l) <= 0.75*y) {
                    Log.i("first","0.75");
                    i1.setVisibility(View.VISIBLE);
                }
                if (TimeUnit.MILLISECONDS.toMinutes(l) <= 0.5*y) {
                    Log.i("second","0.5");
                    i2.setVisibility(View.VISIBLE);
                }
                if (TimeUnit.MILLISECONDS.toMinutes(l) <= 0.25*y)
                {
                    Log.i("third","0.25");
                    i3.setVisibility(View.VISIBLE);
                }
                if (TimeUnit.MILLISECONDS.toMinutes(l) <= 0.05*y) {
                    Log.i("four","0.15");
                    i4.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onFinish() {
                time.setText("Congratuation!");
                dbhelper.change(x, "total", y);
                dbhelper.change(x, "frequency", 1);

                Thread timer = new Thread()
                {
                    public void run()
                    {
                        try
                        {
                            sleep(2000);

                        }
                        catch(InterruptedException e)
                        {
                            e.printStackTrace();

                        }
                        finally
                        {
                            Intent intent = new Intent(getApplicationContext(),Register.class );
                            startActivity(intent);

                        }

                    }

                };

                timer.start();


            }



        };
        timer.start();

        //time.setText(time.getText() + String.valueOf(y * 1000 / 1000));


        arr_str.add("Come on " + x + "," + "Its only " + String.valueOf(y * 1000 / 1000)+"minutes");
        arr_str.add("Big Brother is watching you studying!");
        arr_str.add("Good luck");
        arr_str.add(x+"You can do it!");
    }



    /**
     * When pressing giveUp,some warning messages will pop up to remind user to study
     *
     * @param  view currentView

     */
    public void btnGiveUp(View view){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(x+ " Are you sure?");
        alert.setMessage("Come on " + x + "," + "Its only " + y + "minutes!");


        alert.setNegativeButton("continue", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                Random randomGenerator = new Random();
                int ramdomInt = randomGenerator.nextInt(4);
                String y = arr_str.get(ramdomInt);

                dialog.cancel();

                Toast.makeText(getApplicationContext(), (String)y,
                        Toast.LENGTH_SHORT).show();

                // this will generate random number b/w 0 to 4
            }


        });

        alert.create().show();

    }




    }







