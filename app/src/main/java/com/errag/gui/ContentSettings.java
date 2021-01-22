package com.errag.gui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.errag.models.SelectionViewItem;
import com.errag.models.Variable;
import com.errag.opentaskworker.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ContentSettings extends ContentElement implements View.OnClickListener, AdapterView.OnItemClickListener {
    public ContentSettings(LinearLayout _layout) {
        super(_layout);
    }

    private HorizontalScrollView viewVariablesList = null;
    private LinearLayout viewVariablesLayout = null;
    private ListView listViewVariables = null;
    private VariableAdapter adapter;


    @Override
    protected void doInit() {
        try {
            if(!isInit) {
                viewVariablesList = (HorizontalScrollView) this.guiAction.getActivity().findViewById(R.id.scrollSettingVariables);
                viewVariablesLayout = (LinearLayout) viewVariablesList.getChildAt(0);
                listViewVariables = (ListView) this.guiAction.getActivity().findViewById(R.id.listViewSettingVariables);

                listViewVariables.setAdapter(adapter);
                listViewVariables.setOnItemClickListener(this);
            }

            SelectionViewItem[] availableVariables = this.guiAction.getTaskController().getAvailableVariables();
            generateSelectionView(viewVariablesList, viewVariablesLayout, availableVariables, this);

            adapter = new VariableAdapter(
                    new ArrayList<>(this.guiAction.getTaskController().getUsedVariables()),
                    this.guiAction.getActivity()
            );
            listViewVariables.setAdapter(adapter);

        } catch(Exception ex) {
            this.guiAction.showMessage(ex.getMessage());
        }
    }

    public void addVariable(View view) throws Exception {
        Variable variable = ((Variable) view.getTag());

        if(!this.guiAction.getTaskController().containerVariable(variable)) {
            if (variable.hasSelection()) {
                Variable clone = Variable.createByMove(variable);
                this.guiAction.getTaskController().addVariable(clone);
            } else {
                this.removeVariable(view);
            }

            variable.resetInputParameter();
        }
        doInit();
    }

    public void removeVariable(View view) {
        Object item = view.getTag();

        if(item instanceof Variable) {
            this.guiAction.getTaskController().removeVariable((Variable) item);
            doInit();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setTag(parent.getAdapter().getItem(position));
        ParameterDialog parameterDialog = new ParameterDialog(this.guiAction, view);
        parameterDialog.show();
    }
}
