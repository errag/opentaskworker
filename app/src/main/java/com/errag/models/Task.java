package com.errag.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class Task extends BroadcastReceiver {
    private boolean active = false;
    private String name = null;
    private List<Sensor> sensors = null;
    private List<Action> actions = null;

    public Task(String _name, List<Sensor> _sensors, List<Action> _actions) throws Exception {
        if(_sensors.size() == 0)
            throw new Exception("You need at least one sensor!");
        else if(_actions.size() == 0)
            throw new Exception("You need at least one action!");

        this.name = _name;
        this.sensors = _sensors;
        this.actions = _actions;
        this.active = true;
    }

    public void registrateRecever(Context context) {
        for(Sensor sensor : sensors)
            context.registerReceiver(this, sensor.getIntentFilter());
    }

    public void unregistrateReceiver(Context context) {
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (this.isActive()) {
                Sensor listeningSensor = this.getSensorByAction(intent.getAction());
                Boolean isTrigger = listeningSensor.isSensorTrigger(context, intent);

                if (isTrigger) {
                    for (Action action : this.actions) {
                        action.exec(context);
                    }
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public SelectionViewItem[] getSelectionViewItems() {
        List<SelectionViewItem> items = new ArrayList<>();
        items.addAll(this.sensors);
        items.addAll(this.actions);

        return items.toArray(new SelectionViewItem[items.size()]);
    }

    private Sensor getSensorByAction(String _actionName) {
        Sensor listeningSensor = null;

        for(Sensor sensor : this.sensors) {
            if(sensor.getActionName().equals(_actionName)) {
                listeningSensor = sensor;
                break;
            }
        }

        return listeningSensor;
    }

    /*** GETTER & SETTER ***/

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
