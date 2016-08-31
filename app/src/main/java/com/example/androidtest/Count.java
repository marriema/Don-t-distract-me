package com.example.androidtest;
        import java.util.Random;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.CountDownTimer;
        import android.text.Editable;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.content.DialogInterface;
        import android.widget.Toast;
        import java.util.ArrayList;
        import java.lang.String;
        import java.lang.Object;



public class Count extends Activity{


    private CountDownTimer timer;
    private String x;
    private int y;
    private ArrayList<String> arr_str;
    Button back;
    TextView time;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        x = ((MyApplication)getApplication()).getUsername();
        y = ((MyApplication)getApplication()).getTime();
        time = (TextView) findViewById(R.id.textView);
        back = (Button) findViewById(R.id.button);
        arr_str = new ArrayList<String>(5);

        startService(new Intent(this, ActivityList.class));

        timer = new CountDownTimer(y * 1000, 1 * 1000) {
            @Override
            public void onTick(long l) {
                time.setText("" + l / 1000);

            }
            @Override
            public void onFinish() {
                time.setText("Congratuation!");
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

        time.setText(time.getText() + String.valueOf(y * 1000 / 1000));


        arr_str.add("Come on " + x + "," + "Its only " + String.valueOf(y * 1000 / 1000)+"minutes");
        arr_str.add("Big Brother is watching you studying!");
        arr_str.add("Good luck");
        arr_str.add(x+"You can do it!");
    }



    // create all possible messages
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







