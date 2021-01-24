package com.errag.opentaskworker;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.errag.models.Task;

import java.util.Timer;
import java.util.TimerTask;

public class OTWService extends Service {

    private static TaskController taskController = null;

    public static boolean RESTART = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public OTWService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);

        taskController = TaskController.getInstance();
        this.registrateTasks();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        this.unregistrateTasks();

        Intent broadcastIntent = new Intent(this, OTWService.class);
        broadcastIntent.setAction("otw.service.restart");
        broadcastIntent.setClass(this, RestartBroadcastReceiver.class);
        this.sendBroadcast(broadcastIntent);

        System.out.println("DESTROY");
        super.onDestroy();
    }

    private void registrateTasks() {
        for(Task task : taskController.getTasks())
            task.registrateRecever(this);
    }

    private void unregistrateTasks() {
        for(Task task : taskController.getTasks())
            task.unregistrateReceiver(this);
    }

    int counter;
    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        if(oldTime == 0)
            return;
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
