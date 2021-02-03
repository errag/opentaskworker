package com.errag.models;

import android.content.Context;

public abstract class Action extends SelectionViewItem {
    protected enum CODE {
        ON,
        OFF,
        TOGGLE
    }

    public abstract boolean exec(Context context, ParameterContainer params) throws Exception;

    public boolean exec(Context context) throws Exception {
        return this.exec(context, new ParameterContainer(this.getInputParameters()));
    }

    // static methods
    public static Action createByMove(Action _action) throws Exception {
        Class<?> classClone = Class.forName(_action.getClass().getName());
        Action actionClone = (Action)classClone.newInstance();
        actionClone.changeInputParameter(_action.getInputParameters());
        _action.resetInputParameter();

        return actionClone;
    }
}
