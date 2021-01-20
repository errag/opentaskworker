package com.errag.gui;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.errag.models.SelectionViewItem;
import com.errag.models.Variable;
import com.errag.opentaskworker.R;

import java.util.ArrayList;

public class ContentSettings extends ContentElement implements View.OnClickListener {
    public ContentSettings(LinearLayout _layout) {
        super(_layout);
    }

    private HorizontalScrollView viewVariablesList = null;
    private LinearLayout viewVariablesLayout = null;
    private ListView listViewVariables = null;
    private VariableAdapter adapter;

    @Override
    protected void doInit() {
        viewVariablesList = (HorizontalScrollView)this.guiAction.getActivity().findViewById(R.id.scrollSettingVariables);
        viewVariablesLayout = (LinearLayout)viewVariablesList.getChildAt(0);
        listViewVariables = (ListView)this.guiAction.getActivity().findViewById(R.id.listViewSettingVariables);

        SelectionViewItem[] availableVariables = this.guiAction.getTaskController().getVariables();
        generateSelectionView(viewVariablesList, viewVariablesLayout, availableVariables, this);

        adapter = new VariableAdapter(new ArrayList<>(), this.guiAction.getActivity());
        listViewVariables.setAdapter(adapter);
    }

    public void addVariable(View view) throws Exception {
        Variable variable = ((Variable) view.getTag());

        if(variable.hasSelection()) {
            Variable clone = Variable.createByMove(variable);
            adapter.add(clone);
        }

        variable.resetInputParameter();

    }

    public void removeVariable(View view) {

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
