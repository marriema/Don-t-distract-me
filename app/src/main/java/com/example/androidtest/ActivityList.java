package com.example.androidtest;

/**
 * Created by marriema on 5/25/16.
 */

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ActivityList extends Service{

    private ActivityManager activityManager;

    private String lastTaskName;

    private Timer timer;
    private TimerTask task = new TimerTask(){

        @Override
        public void run() {
// TODO Auto-generated method stub
            if(activityManager == null){
                activityManager = (ActivityManager) ActivityList.this.getSystemService(ACTIVITY_SERVICE);
            }

            List<RecentTaskInfo> recentTasks = activityManager.getRecentTasks(2, ActivityManager.RECENT_WITH_EXCLUDED);

            RecentTaskInfo recentInfo = recentTasks.get(0);
            Intent intent = recentInfo.baseIntent;
            String recentTaskName = intent.getComponent().getPackageName();
            Log.v("currentTask",recentTaskName);

            if(lastTaskName != null){
                if(!lastTaskName.equals(recentTaskName) && !recentTaskName.equals("com.example.androidtest.Count") && !recentTaskName.equals("com.example.androidtest.Register"))
                {
                    Log.d("recentPackageName", recentTaskName);
                    System.out.println("service is running");
                    Intent intentNewActivity = new Intent(ActivityList.this, Back.class);
                    intentNewActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentNewActivity);
//Toast.makeText(XuebaService.this, lastTaskName, Toast.LENGTH_SHORT).show()
                }
            }

            lastTaskName = recentTaskName;

        }

    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        timer = new Timer();
        timer.schedule(task, 0, 20);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
// TODO Auto-generated method stub
        return null;
    }


}

