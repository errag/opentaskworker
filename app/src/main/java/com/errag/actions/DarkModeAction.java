package com.errag.actions;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class DarkModeAction extends Action {

    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        boolean darkModeOn = getACBoolean(State.DarkMode.ON.toString(), params);
        boolean darkModeOff = getACBoolean(State.DarkMode.OFF.toString(), params);

        if(darkModeOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else if(darkModeOff)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_darkmode;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[]{
                new Parameter(R.string.darkmode_on, State.DarkMode.ON.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.darkmode_off, State.DarkMode.OFF.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
