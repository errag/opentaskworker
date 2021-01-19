package com.errag.models;

import android.content.Context;

public abstract class Action extends SelectionViewItem {
    protected enum CODE {
        ON,
        OFF,
        TOGGLE
    }

    public abstract boolean exec(Context context, Parameter[] params) throws Exception;

    // static methods
    public static Action createByMove(Action _action) throws Exception {
        Class<?> classClone = Class.forName(_action.getClass().getName());
        Action actionClone = (Action)classClone.newInstance();
        actionClone.changeInputParameter(_action.getInputParameters());
        _action.resetInputParameter();

        return actionClone;
    }

    public static String getACString(String state, Parameter[] parameters) {
        String input = null;

        for(Parameter parameter : parameters)
        {
            if(parameter.getValue().equals(state))
            {
                input = parameter.getInput();
                break;
            }
        }

        return input;
    }

    public static Integer getACInteger(String state, Parameter[] parameters) {
        Integer input = null;
        String inputString = Action.getACString(state, parameters);

        if(inputString != null)
            input = Integer.parseInt(inputString);

        return input;
    }

    public static Boolean getACBoolean(String state, Parameter[] parameters) {
        return Boolean.parseBoolean(Action.getACString(state, parameters));
    }
}
