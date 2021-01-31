package com.errag.actions;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class ButtonAction extends Action {
    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        boolean buttonHome = getACBoolean(State.Button.HOME.toString(), params);

        if(buttonHome) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startMain);
        }

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_button;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.button_home, State.Button.HOME.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
