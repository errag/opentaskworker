package com.errag.sensors;

import android.content.Context;
import android.content.Intent;

import com.errag.models.Parameter;
import com.errag.models.Sensor;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class WallpaperSensor extends Sensor {
    @Override
    public String getActionName() {
        return Intent.ACTION_SET_WALLPAPER;
    }

    @Override
    public String getState(Context context, Intent intent) {
        return State.Wallpaper.CHANGED.toString();
    }

    @Override
    public int getImage() {
        return R.drawable.img_wallpaper;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.wallpaper_changed, State.Wallpaper.CHANGED.toString(), Parameter.Type.BOOLEAN)
        };
    }
}
