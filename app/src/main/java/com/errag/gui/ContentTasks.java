package com.errag.gui;

import android.widget.LinearLayout;

public class ContentTasks extends ContentElement {
    public ContentTasks(LinearLayout _layout) {
        super(_layout);
    }

    @Override
    protected void doInit() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public MODULE getModule() {
        return MODULE.TASKS;
    }
}
