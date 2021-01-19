package com.spif.opentaskworker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.spif.models.Sensor;

public class EventController extends BroadcastReceiver {

    private Context context = null;
    private TaskController taskController = null;

    public EventController()
    {

    }

    public EventController(Context _context, TaskController _taskController)
    {
        this.context = _context;
        this.taskController = _taskController;
        this.addReceiver();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Sensor sensor = taskController.getSensorByAction(action);
        String state = sensor.getState(context, intent);

        System.out.println(action + " = " + state);
    }

    public void addReceiver() {
        for(Sensor sensor : taskController.getSensors())
            this.context.registerReceiver(this, sensor.getIntentFilter());
    }
}
