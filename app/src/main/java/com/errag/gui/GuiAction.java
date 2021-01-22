package com.errag.gui;

import android.app.Activity;
import android.view.View;

import com.errag.opentaskworker.TaskController;

public interface GuiAction {
    public Activity getActivity();
    public TaskController getTaskController();
    public void sendGuiAction(AC action, AC parameter, View target, View.OnClickListener source);

    public enum AC {
        CHANGE_MENU,
        CHANGE_LAYOUT_DASHBOARD,
        CHANGE_LAYOUT_TASKS,
        CHANGE_LAYOUT_NEW,
        CHANGE_LAYOUT_SETTINGS,

        DIALOG_CLOSE,

        NEW_TRIGGER,
        NEW_ACTION,
        NEW_ACTION_DELETE,

        SETTING_NEW_VARIABLE,
        SETTING_DELETE_VARIABLE,

        VIEW_REFRESH
    }
}
