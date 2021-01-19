package com.errag.actions;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.spif.opentaskworker.R;

public class BrightnessAction extends Action {
    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        int brightness = Integer.parseInt(params[0].getInput());

        ContentResolver cResolver = context.getApplicationContext().getContentResolver();
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);

        return true;
    }

    @Override
    public int getImage() {
        return R.drawable.img_brightness;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[]{
                new Parameter(R.string.brightness_level, State.Brightness.LEVEL.toString(), Parameter.Type.INTEGER)
        };
    }
}
