package com.errag.gui;

import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.errag.opentaskworker.MainActivity;
import com.errag.opentaskworker.R;

public class ContentDashboard extends ContentElement implements CompoundButton.OnCheckedChangeListener {

    private Switch switchService = null;

    public ContentDashboard(LinearLayout _layout) {
        super(_layout);
    }

    @Override
    protected void doInit() {
        if(!isInit) {
            this.switchService = (Switch)this.guiAction.getActivity().findViewById(R.id.switchDashboardService);
        }
    }

    @Override
    public void updateUI() {
        this.switchService.setChecked(MainActivity.isServiceRunning());
        this.switchService.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == switchService) {
            if(isChecked)
                MainActivity.startService();
            else
                MainActivity.stopService();
        }
    }

    @Override
    public MODULE getModule() {
        return MODULE.DASHBOARD;
    }
}
