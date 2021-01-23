package com.errag.gui;

import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
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

            this.switchService.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == switchService)
            this.guiAction.getTaskController().setService(isChecked);
    }

    @Override
    public MODULE getModule() {
        return MODULE.DASHBOARD;
    }
}
