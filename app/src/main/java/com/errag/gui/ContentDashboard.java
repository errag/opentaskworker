package com.errag.gui;

import android.widget.LinearLayout;

public class ContentDashboard extends ContentElement {
    public ContentDashboard(LinearLayout _layout) {
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
        return MODULE.DASHBOARD;
    }
}
