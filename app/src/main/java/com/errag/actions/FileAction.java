package com.errag.actions;

import android.content.Context;

import com.errag.models.Action;
import com.errag.models.Parameter;

public class FileAction extends Action {
    @Override
    public boolean exec(Context context, Parameter[] params) throws Exception {
        return false;
    }

    @Override
    public int getImage() {
        return 0;
    }

    @Override
    public Parameter[] setInputParameter() {
        return new Parameter[0];
    }
}
