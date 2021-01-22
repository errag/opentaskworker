package com.errag.actions;

import android.app.Activity;
import android.content.Context;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.opentaskworker.PermissionController;
import com.errag.opentaskworker.R;

public class FileAction extends Action {
    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {

        return false;
    }

    @Override
    public int getImage() {
        return R.drawable.img_file;
    }

    @Override
    public void askForPermissions(Activity activity) {
        PermissionController.askForFileIO(activity);
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[0];
    }
}
