package com.errag.models;

import android.content.Context;

public abstract class Action extends SelectionViewItem {
    protected enum CODE {
        ON,
        OFF,
        TOGGLE
    }
    
    protected CODE toggleOnOff(boolean state, CODE turn) {
        if(turn.equals(CODE.TOGGLE))
        {
            if(state)
                turn = CODE.OFF;
            else
                turn = CODE.ON;
        }
        
        return turn;
    }

    public abstract boolean exec(Context context, Parameter[] params) throws Exception;

    public static Action createByMove(Action _action) throws Exception {
        Class<?> classClone = Class.forName(_action.getClass().getName());
        Action actionClone = (Action)classClone.newInstance();
        actionClone.changeInputParameter(_action.getInputParameters());
        _action.resetInputParameter();

        return actionClone;
    }
}
