package com.errag.actions;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.State;
import com.errag.opentaskworker.R;

import java.io.File;
import java.io.FileOutputStream;

public class ScreenshotAction extends Action {
    @Override
    public boolean exec(Context context, ParameterContainer params) throws Exception {

        View v1 = ((Activity)context).getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);

        File imageFile = new File("/storage/emulated/0/shot.jpeg");

        FileOutputStream outputStream = new FileOutputStream(imageFile);
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        outputStream.flush();
        outputStream.close();

        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public int getImage() {
        return 0;
    }

    @Override
    public Parameter[] setDialogInputParameter() {
        return new Parameter[] {
                new Parameter(R.string.sms_to_number, State.SMS.SEND_TO.toString(), Parameter.Type.BOOLEAN),
                new Parameter(R.string.sms_message, State.SMS.MESSAGE.toString(), Parameter.Type.STRING)
        };
    }
}
