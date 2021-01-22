package com.errag.models;

import android.app.Activity;

import com.errag.opentaskworker.PermissionController;

public abstract class SelectionViewItem {

    protected Parameter[] inputParameter = null;

    public abstract int getImage();
    public abstract Parameter[] setInputParameter();

    public void initInputParameters() {
        this.inputParameter = this.setInputParameter();
    }

    public Parameter[] getInputParameters() {
        return this.inputParameter;
    }

    public void changeInputParameter(Parameter[] _inputParameter) {
        this.inputParameter = _inputParameter;
    }

    public void askForPermissions(Activity activity) {

    }

    public boolean hasSelection() {
        for(Parameter parameter : this.getInputParameters()) {
            if(parameter != null && parameter.getInput() != null) {
                if (parameter.getType().equals(Parameter.Type.BOOLEAN) && Boolean.parseBoolean(parameter.getInput()))
                    return true;
                else if (parameter.getType().equals(Parameter.Type.INTEGER) && parameter.getInput().length() > 0)
                    return true;
                else if (parameter.getType().equals(Parameter.Type.STRING) && parameter.getInput().length() > 0)
                    return true;
            }
        }

        return false;
    }

    public void resetInputParameter() {
        this.initInputParameters();
    }
}
