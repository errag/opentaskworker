package com.errag.gui;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.errag.models.Parameter;
import com.errag.models.SelectionViewItem;
import com.errag.models.Variable;
import com.errag.opentaskworker.PermissionController;
import com.errag.opentaskworker.R;

import java.io.IOException;

public class ContentSettings extends ContentElement implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public ContentSettings(LinearLayout _layout) {
        super(_layout);
    }

    private final static String SETTING_RESTART_ON_BOOT = "SETTING_RESTART_ON_BOOT";
    private final static String SETTING_ENABLE_LOGGING= "SETTING_ENABLE_LOGGING";

    private HorizontalScrollView viewVariablesList = null;
    private LinearLayout viewVariablesLayout = null;
    private LinearLayout viewLayoutVariables = null;
    private Button buttonBuyMeACoffee = null;
    private Switch switchBoot = null;
    private Switch switchAkku = null;
    private Switch switchLogging = null;

    @Override
    protected void doInit() {
        try {
            if (!isInit) {
                viewVariablesList = (HorizontalScrollView) this.guiAction.getActivity().findViewById(R.id.scrollSettingVariables);
                viewVariablesLayout = (LinearLayout) viewVariablesList.getChildAt(0);
                viewLayoutVariables = (LinearLayout) this.guiAction.getActivity().findViewById(R.id.layoutSettingsVariables);

                switchBoot = (Switch)this.guiAction.getActivity().findViewById(R.id.switchSettingRestart);
                switchAkku = (Switch)this.guiAction.getActivity().findViewById(R.id.switchSettingAkku);
                switchLogging = (Switch)this.guiAction.getActivity().findViewById(R.id.switchSettingLogging);

                switchBoot.setOnCheckedChangeListener(this);
                switchAkku.setOnCheckedChangeListener(this);
                switchLogging.setOnCheckedChangeListener(this);

                buttonBuyMeACoffee = (Button)this.guiAction.getActivity().findViewById(R.id.buttonSettingsCoffee);
                buttonBuyMeACoffee.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(guiAction.getActivity().getString(R.string.coffee_link)));
                    guiAction.getActivity().startActivity(intent);
                });
            }

        } catch (Exception ex) {
            this.guiAction.showMessage(ex.getMessage());
        }
    }

    @Override
    public void updateUI() {
        try {
            updateSettings();
            updateVariables();
        } catch(Exception ex) {
            this.guiAction.showMessage(ex.getMessage());
        }
    }

    private void updateVariables() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        SelectionViewItem[] availableVariables = this.guiAction.getTaskController().getAvailableVariables();
        generateSelectionView(viewVariablesList, viewVariablesLayout, availableVariables, this, true);
        refreshVariablesView();
    }

    private void updateSettings() {
        boolean settingBoot = Boolean.parseBoolean(this.guiAction.getTaskController().getSetting(SETTING_RESTART_ON_BOOT));
        boolean settingLogging = Boolean.parseBoolean(this.guiAction.getTaskController().getSetting(SETTING_ENABLE_LOGGING));
        boolean settingAkku = PermissionController.checkForBatteryOptimization(this.guiAction.getActivity());

        if(switchBoot.isChecked() != settingBoot)
            switchBoot.setChecked(settingBoot);

        if(switchLogging.isChecked() != settingLogging)
            switchLogging.setChecked(settingLogging);

        if(switchAkku.isChecked() != settingAkku)
            switchAkku.setChecked(settingAkku);
    }

    private void refreshVariablesView() {
        viewLayoutVariables.removeAllViews();

        for(Variable variable : this.guiAction.getTaskController().getUsedVariables()) {
            View view =  LayoutInflater.from(this.guiAction.getActivity()).inflate(R.layout.variable_item, null);
            view.setTag(variable);
            view.setOnClickListener(this);

            String name = variable.getVariableName();

            if(variable.getInputParameters().length > 1) {
                Parameter parameter = variable.getInputParameters()[1];
                String value = parameter.getInput();

                if (value != null && value.length() > 0) {
                    if (!parameter.getType().equals(Parameter.Type.PASSWORD))
                        name += " = " + value;
                    else
                        name += " = **********";
                }
            }

            ((TextView)view.findViewById(R.id.variable_item_text)).setText(name);
            ((ImageView)view.findViewById(R.id.variable_item_image)).setImageResource(variable.getImage());

            viewLayoutVariables.addView(view);
        }
    }

    public void addVariable(View view) throws Exception {
        Variable variable = ((Variable) view.getTag());

        if (!this.guiAction.getTaskController().containsVariable(variable)) {
            if (variable.hasSelection()) {
                Variable clone = Variable.createByMove(variable);

                if(this.guiAction.getTaskController().getVariableByName(clone.getVariableName()) != null)
                    this.guiAction.showMessage(this.guiAction.getActivity().getString(R.string.error_variable_is_defined));
                else
                    this.guiAction.getTaskController().addVariable(clone);
            } else {
                this.removeVariable(view);
            }

            variable.resetInputParameter();
        }

        refreshVariablesView();
    }

    public void removeVariable(View view) {
        Object item = view.getTag();

        if (item instanceof Variable) {
            this.guiAction.getTaskController().removeVariable((Variable) item);
            refreshVariablesView();
        }
    }

    @Override
    public void onClick(View v) {
        ParameterDialog parameterDialog = new ParameterDialog(this.guiAction, v);
        parameterDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String SETTING = null;

        if(buttonView == switchAkku)
            PermissionController.askForBatteryOptimization(this.guiAction.getActivity());
        else if(buttonView == switchBoot)
            SETTING = SETTING_RESTART_ON_BOOT;
        else if(buttonView == switchLogging)
            SETTING = SETTING_ENABLE_LOGGING;

        if(SETTING != null)
            this.guiAction.getTaskController().changeSetting(SETTING, String.valueOf(isChecked));

        updateSettings();
    }

    @Override
    public MODULE getModule() {
        return MODULE.SETTINGS;
    }
}
