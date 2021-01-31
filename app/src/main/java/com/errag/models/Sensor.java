package com.errag.models;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;

public abstract class Sensor extends SelectionViewItem {

    public abstract String getActionName();
    public abstract String getState(Context context, Intent intent);

    public String[] getActionNames() {
        return null;
    }

    public IntentFilter[] getIntentFilter() {
        ArrayList<IntentFilter> intentFilter = new ArrayList<>();

        if(getActionName() != null)
            intentFilter.add(new IntentFilter(getActionName()));
        else if(getActionNames() != null) {
            for(String action : getActionNames())
                intentFilter.add(new IntentFilter(action));
        }

        return intentFilter.toArray(new IntentFilter[intentFilter.size()]);
    }

    public void registrateCustomIntentFilter(Context context) {

    }

    public boolean validateReceive(String action) {
        return action.equals(getActionName());
    }

    public boolean isAction(String _action) { return getActionName().equals(_action); }

    protected String getSensorValue(Context context, Intent intent) {
        return null;
    }

    public boolean isSensorTrigger(Context context, Intent intent) {
        return Action.getACBoolean(this.getState(context, intent), this.getInputParameters());
    }

}
