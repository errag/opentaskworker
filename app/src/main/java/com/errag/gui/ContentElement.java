package com.errag.gui;

import android.content.ClipData;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.errag.models.SelectionViewItem;
import com.errag.opentaskworker.R;

import java.util.ArrayList;

public abstract class ContentElement {
    public enum MODULE {
        DASHBOARD,
        TASKS,
        NEW_TASK,
        SETTINGS
    };

    boolean isInit = false;
    protected LinearLayout layout = null;
    protected GuiAction guiAction = null;

    protected final static int TASK_NON_SELECTED = R.drawable.tasks_button;
    protected final static int TASK_TRIGGER = R.drawable.tasks_button_trigger;
    protected final static int TASK_ACTION = R.drawable.tasks_button_action;
    protected final static int TASK_SETTING = R.drawable.tasks_button_setting;

    public ContentElement(LinearLayout _layout)
    {
        this.layout = _layout;
    }

    protected abstract void doInit();
    public abstract void updateUI();
    public abstract MODULE getModule();

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

    protected void refresh() {
        this.guiAction.sendGuiAction(GuiAction.AC.VIEW_REFRESH, null, this.getLayout(), null);
    }

    protected void generateSelectionView(
            HorizontalScrollView scrollLayout, LinearLayout layout, SelectionViewItem[] items, View.OnClickListener onClick)
    {
        scrollLayout.setBackgroundResource(R.drawable.tasks_list);
        layout.setPadding(10, 20, 10, 20);

        if(items != null)
        {
            for(SelectionViewItem item : items) {
                item.resetInputParameter();
                ImageButton button = getSelectionViewButton(item, onClick, false);
                layout.addView(button);
            }
        }
    }

    protected ImageButton getSelectionViewButton(SelectionViewItem item, View.OnClickListener onClick, boolean dragable) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 0, 10, 0);

        ImageButton button = new ImageButton(this.guiAction.getActivity());
        button.setTag(item);
        button.setLayoutParams(params);

        button.setImageResource(item.getImage());
        button.setAlpha((float)0.95);
        button.setOnClickListener(onClick);

        if(dragable) {
            button.setOnLongClickListener(v -> {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                return true;
            });
        }

        if(getModule().equals(MODULE.NEW_TASK)) {
            setImageButtonTriggerStyle(button, TASK_NON_SELECTED, TASK_TRIGGER);
        } else if(getModule().equals(MODULE.SETTINGS)) {
            button.setBackgroundResource(TASK_SETTING);
            button.setAlpha((float)0.80);
        }

        return button;
    }

    protected void setImageButtonTriggerStyle(View button, int notSelected, int selected) {
        SelectionViewItem item = (SelectionViewItem)button.getTag();
        button.setBackgroundResource(!item.hasSelection() ? notSelected : selected);
    }
}
