package com.errag.actions;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class VibratorAction extends Action {
    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        Integer vibratorMS = getACInteger(State.Vibrator.MS.toString(), params);

        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(VibrationEffect.createOneShot(vibratorMS, VibrationEffect.DEFAULT_AMPLITUDE));

        return false;
    }

    @Override
    public int getImage() {
        return 0;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.vibrator_ms, State.Vibrator.MS.toString(), Parameter.Type.INTEGER)
        };
    }
}
