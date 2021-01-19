package com.spif.gui;

import android.widget.LinearLayout;

import androidx.appcompat.widget.ContentFrameLayout;

public abstract class ContentElement {
    boolean isInit = false;
    protected LinearLayout layout = null;
    protected GuiAction guiAction = null;

    public ContentElement(LinearLayout _layout)
    {
        this.layout = _layout;
    }

    protected abstract void doInit();
    public abstract void updateUI();

    public void init(GuiAction _guiAction) {
        if(!isInit()) {
            this.isInit = true;
            this.guiAction = _guiAction;
            doInit();
        }
    }

    public boolean isInit() { return this.isInit; }

    public LinearLayout getLayout() {
        return this.layout;
    }
}
