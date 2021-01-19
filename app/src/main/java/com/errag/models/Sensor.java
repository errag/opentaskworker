package com.errag.models;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class Sensor extends SelectionViewItem {

    public abstract String getActionName();
    public abstract String getState(Context context, Intent intent);

    public IntentFilter getIntentFilter() {
        return new IntentFilter(getActionName());
    }

    public boolean validateReceive(String action) {
        return action.equals(getActionName());
    }

    public boolean isAction(String _action) { return getActionName().equals(_action); }

    protected String getSensorValue(Context context, Intent intent) {
        return null;
    }


}
