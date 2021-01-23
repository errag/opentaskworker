package com.errag.gui;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.errag.models.Parameter;
import com.errag.models.SelectionViewItem;
import com.errag.models.Variable;
import com.errag.opentaskworker.R;

public class ContentSettings extends ContentElement implements View.OnClickListener {
    public ContentSettings(LinearLayout _layout) {
        super(_layout);
    }

    private HorizontalScrollView viewVariablesList = null;
    private LinearLayout viewVariablesLayout = null;
    private LinearLayout viewLayoutVariables = null;
    private Button buttonBuyMeACoffee = null;


    @Override
    protected void doInit() {
        try {
            if (!isInit) {
                viewVariablesList = (HorizontalScrollView) this.guiAction.getActivity().findViewById(R.id.scrollSettingVariables);
                viewVariablesLayout = (LinearLayout) viewVariablesList.getChildAt(0);
                viewLayoutVariables = (LinearLayout) this.guiAction.getActivity().findViewById(R.id.layoutSettingsVariables);

                buttonBuyMeACoffee = (Button)this.guiAction.getActivity().findViewById(R.id.buttonSettingsCoffee);
                buttonBuyMeACoffee.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(guiAction.getActivity().getString(R.string.coffee_link)));
                    guiAction.getActivity().startActivity(intent);
                });
            }

            SelectionViewItem[] availableVariables = this.guiAction.getTaskController().getAvailableVariables();
            generateSelectionView(viewVariablesList, viewVariablesLayout, availableVariables, this, true);

            refreshVariablesView();

        } catch (Exception ex) {
            this.guiAction.showMessage(ex.getMessage());
        }
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
    public void updateUI() {

    }

    @Override
    public void onClick(View v) {
        ParameterDialog parameterDialog = new ParameterDialog(this.guiAction, v);
        parameterDialog.show();
    }

    @Override
    public MODULE getModule() {
        return MODULE.SETTINGS;
    }
}
