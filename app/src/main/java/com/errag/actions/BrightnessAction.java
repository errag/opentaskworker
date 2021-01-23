package com.errag.actions;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.PermissionController;
import com.errag.opentaskworker.R;

public class BrightnessAction extends Action {
    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        Integer brightness = getACInteger(State.Brightness.LEVEL.toString(), params);

        if(brightness != null) {
            ContentResolver cResolver = context.getApplicationContext().getContentResolver();
            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        }

        return true;
    }

    @Override
    public void askForPermissions(Activity activity) {
        PermissionController.askForWriteSettings(activity);
    }

    @Override
    public int getImage() {
        return R.drawable.img_brightness;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[]{
                new Parameter(R.string.brightness_level, State.Brightness.LEVEL.toString(), Parameter.Type.INTEGER)
        };
    }
}
