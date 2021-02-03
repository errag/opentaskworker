package com.errag.actions;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.State;
import com.errag.opentaskworker.R;

public class VibratorAction extends Action {
    @Override
    public boolean exec(Context context, ParameterContainer params) throws Exception {
        Integer vibratorMS = params.getInteger(State.Vibrator.MS.toString());

        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(VibrationEffect.createOneShot(vibratorMS, VibrationEffect.DEFAULT_AMPLITUDE));

        return false;
    }

    @Override
    public int getImage() {
        return R.drawable.img_vibrate;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.vibrator_ms, State.Vibrator.MS.toString(), Parameter.Type.INTEGER)
        };
    }
}
