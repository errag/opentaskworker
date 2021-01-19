package com.errag.actions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class RotateAction extends Action {

    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        boolean orientationPortrait = getACBoolean(State.Orientation.PORTRAIT.toString(), params);
        boolean orientationLandscape = getACBoolean(State.Orientation.LANDSCAPE.toString(), params);

        if(orientationPortrait)
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_rotate;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[]{
                new Parameter(R.string.orientation_portrait, State.Orientation.PORTRAIT.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.orientation_landscape, State.Orientation.LANDSCAPE.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
