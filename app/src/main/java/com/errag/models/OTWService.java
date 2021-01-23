package com.errag.models;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.errag.opentaskworker.TaskController;

import java.util.Timer;
import java.util.TimerTask;

public class OTWService extends Service {

    private static TaskController taskController = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public OTWService() {
        /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            customStartForeground();
        else*/
        //    startForeground(1, new Notification());
    }

    private void customStartForeground() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);

        taskController = TaskController.getInstance();

        for(Task task : taskController.getTasks())
            task.registrateRecever(this);

        startTimer();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        for(Task task : taskController.getTasks())
            task.unregistrateReceiver(this);

        Intent broadcastIntent = new Intent(this, OTWService.class);
        sendBroadcast(broadcastIntent);
        stoptimertask();

        System.out.println("DESTROY");
    }

    public static boolean isRunning(Context context) {
        Class<?> serviceClass = OTWService.class;

        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName()))
                return true;
        }

        return false;
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
