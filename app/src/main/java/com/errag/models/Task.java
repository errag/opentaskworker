package com.errag.models;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private boolean active = false;
    private String name = null;
    private List<Sensor> sensors = null;
    private List<Action> actions = null;

    public Task() {

    }

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

    public SelectionViewItem[] getSelectionViewItems() {
        List<SelectionViewItem> items = new ArrayList<>();
        items.addAll(this.sensors);
        items.addAll(this.actions);

        return items.toArray(new SelectionViewItem[items.size()]);
    }

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
