package com.errag.gui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.errag.opentaskworker.R;

public class HeaderMenu implements View.OnClickListener
{
    private static HeaderMenu headerMenu = null;
    private static GuiAction guiAction = null;

    private Button buttonDashboard = null;
    private Button buttonTasks = null;
    private Button buttonNew = null;
    private Button buttonSettings = null;
    private TextView textMenu = null;

    public static HeaderMenu getInstance() {
        return headerMenu;
    }

    public static HeaderMenu createInstance(GuiAction _guiAction) {
        headerMenu = new HeaderMenu(_guiAction);

        return headerMenu;
    }

    private HeaderMenu(GuiAction _guiAction) {
        guiAction = _guiAction;

        buttonDashboard = (Button)guiAction.getActivity().findViewById(R.id.buttonDashboard);
        buttonTasks = (Button)guiAction.getActivity().findViewById(R.id.buttonTasks);
        buttonNew = (Button)guiAction.getActivity().findViewById(R.id.buttonNew);
        buttonSettings = (Button)guiAction.getActivity().findViewById(R.id.buttonSettings);
        textMenu = (TextView)guiAction.getActivity().findViewById(R.id.textMenu);

        buttonDashboard.setTag(GuiAction.AC.CHANGE_LAYOUT_DASHBOARD);
        buttonTasks.setTag(GuiAction.AC.CHANGE_LAYOUT_TASKS);
        buttonNew.setTag(GuiAction.AC.CHANGE_LAYOUT_NEW);
        buttonSettings.setTag(GuiAction.AC.CHANGE_LAYOUT_SETTINGS);

        buttonDashboard.setOnClickListener(this);
        buttonTasks.setOnClickListener(this);
        buttonNew.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        guiAction.sendGuiAction(GuiAction.AC.CHANGE_MENU, (GuiAction.AC)v.getTag(), v, this);
    }

    public void changeTextMenu(CharSequence text, int color)
    {
        this.textMenu.setText(text.toString().toUpperCase());
        this.textMenu.setBackgroundColor(color);
    }
}
