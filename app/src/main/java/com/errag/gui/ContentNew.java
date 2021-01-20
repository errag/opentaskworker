package com.errag.gui;

import android.content.ClipData;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.errag.models.Action;
import com.errag.models.SelectionViewItem;
import com.errag.opentaskworker.R;

import java.util.ArrayList;
import java.util.List;

public class ContentNew extends ContentElement implements View.OnClickListener {
    public ContentNew(LinearLayout _layout) {
        super(_layout);
    }

    private HorizontalScrollView viewTriggerList = null;
    private LinearLayout viewTriggerLayout = null;
    private HorizontalScrollView viewActionList = null;
    private LinearLayout viewActionLayout = null;
    private HorizontalScrollView viewActionSelection = null;
    private LinearLayout viewActionSelectionLayout = null;

    @Override
    protected void doInit() {
        viewTriggerList = (HorizontalScrollView)this.guiAction.getActivity().findViewById(R.id.scrollNewTriggerList);
        viewActionList = (HorizontalScrollView)this.guiAction.getActivity().findViewById(R.id.scrollNewActionList);
        viewActionSelection = (HorizontalScrollView)this.guiAction.getActivity().findViewById(R.id.scrollNewActionSelection);

        viewTriggerLayout = (LinearLayout)viewTriggerList.getChildAt(0);
        viewActionLayout = (LinearLayout)viewActionList.getChildAt(0);
        viewActionSelectionLayout = (LinearLayout)viewActionSelection.getChildAt(0);

        SelectionViewItem[] availableSensors = this.guiAction.getTaskController().getSensors();
        generateSelectionView(viewTriggerList, viewTriggerLayout, availableSensors, this);

        SelectionViewItem[] availableActions = this.guiAction.getTaskController().getActions();
        generateSelectionView(viewActionList, viewActionLayout, availableActions, this);

        generateSelectionView(viewActionSelection, viewActionSelectionLayout, null, this);

        initListeners();
    }

    @Override
    public void updateUI() {
        for(int i=0; i<viewTriggerLayout.getChildCount(); i++)
            setImageButtonTriggerStyle(viewTriggerLayout.getChildAt(i), TASK_NON_SELECTED, TASK_TRIGGER);

        for(int i=0; i<viewActionSelectionLayout.getChildCount(); i++)
        {
            View view = viewActionSelectionLayout.getChildAt(i);
            SelectionViewItem item = (SelectionViewItem)view.getTag();

            if(!item.hasSelection())
                viewActionSelectionLayout.removeView(view);
        }
    }

    private void initListeners() {
        viewActionSelection.setOnDragListener((v, event) -> {
            if(event.getAction() == DragEvent.ACTION_DROP) {
                View view = (View)event.getLocalState();
                ViewGroup owner = (ViewGroup)view.getParent();
                owner.removeView(view);

                // insert
                boolean inserted = false;

                for(int i=owner.getChildCount() - 1; i>=0; i--) {
                    if(owner.getChildAt(i).getX() < event.getX())
                    {
                        owner.addView(view, i + 1);
                        inserted = true;
                        break;
                    }
                }

                if(!inserted)
                    owner.addView(view, 0);
            }

            return true;
        });
    }

    public void addAction(View view) throws Exception {
        if(viewActionSelectionLayout != view.getParent()) {
            Action action = ((Action) view.getTag());

            if(action.hasSelection()) {
                Action clone = Action.createByMove(action);

                View button = getSelectionViewButton(clone, this,true);
                button.setBackgroundResource(TASK_ACTION);
                this.viewActionSelectionLayout.addView(button);
            }
        }
    }

    public void removeAction(View view) throws Exception {
        if(viewActionSelectionLayout == view.getParent())
            viewActionSelectionLayout.removeView(view);
    }

    @Override
    public void onClick(View v) {
        ParameterDialog parameterDialog = new ParameterDialog(this.guiAction, v);
        parameterDialog.show();
    }

    @Override
    public MODULE getModule() {
        return MODULE.NEW_TASK;
    }
}
