package com.errag.gui;

import android.content.ClipData;
import android.content.DialogInterface;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.errag.models.Action;
import com.errag.models.SelectionViewItem;
import com.errag.models.Sensor;
import com.errag.models.Task;
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
    private EditText editTextTaskName = null;
    private Button buttonTaskSave = null;

    @Override
    protected void doInit() {
        try {
            if (!this.isInit) {
                editTextTaskName = (EditText) this.guiAction.getActivity().findViewById(R.id.editTextNewTaskName);
                buttonTaskSave = (Button) this.guiAction.getActivity().findViewById(R.id.buttonNewSaveTask);

                viewTriggerList = (HorizontalScrollView) this.guiAction.getActivity().findViewById(R.id.scrollNewTriggerList);
                viewActionList = (HorizontalScrollView) this.guiAction.getActivity().findViewById(R.id.scrollNewActionList);
                viewActionSelection = (HorizontalScrollView) this.guiAction.getActivity().findViewById(R.id.scrollNewActionSelection);

                viewTriggerLayout = (LinearLayout) viewTriggerList.getChildAt(0);
                viewActionLayout = (LinearLayout) viewActionList.getChildAt(0);
                viewActionSelectionLayout = (LinearLayout) viewActionSelection.getChildAt(0);

                initListeners();
            }

            editTextTaskName.setText("");

            SelectionViewItem[] availableSensors = this.guiAction.getTaskController().getAvailableSensors();
            generateSelectionView(viewTriggerList, viewTriggerLayout, availableSensors, this, true);

            SelectionViewItem[] availableActions = this.guiAction.getTaskController().getAvailableActions();
            generateSelectionView(viewActionList, viewActionLayout, availableActions, this, true);

            generateSelectionView(viewActionSelection, viewActionSelectionLayout, null, this, true);
        } catch(Exception ex) {
            ex.printStackTrace();
            this.guiAction.showMessage(ex.getMessage());
        }
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
            return onSelectViewItemDroped(v, event);
        });

        buttonTaskSave.setOnClickListener(v -> {
            saveTask(false);
        });
    }

    public void addAction(View view) throws Exception {
        System.out.println("addAction");
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

    private boolean onSelectViewItemDroped(View v, DragEvent event) {
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
    }

    private boolean saveTask(boolean overwrite) {
        boolean success = true;

        try {
            String taskName = editTextTaskName.getText().toString();
            Task task = this.guiAction.getTaskController().getTask(taskName);

            if (taskName.length() == 0) {
                throw new Exception(this.guiAction.getActivity().getString(R.string.error_emtpy_filename));
            } else {
                List<Sensor> sensors = getSelectedSensors();
                List<Action> actions = getSelectedActions();

                if(task == null)
                    this.guiAction.getTaskController().addTask(taskName, sensors, actions);
                else {
                    if(!overwrite) {
                        this.openConfirmDialog(
                                this.guiAction.getActivity().getString(R.string.alert_warning),
                                this.guiAction.getActivity().getString(R.string.alert_existing_task),
                                (dialog, which) -> {
                                    saveTask(true);
                                }
                        );
                        success = false;
                    } else {
                        this.guiAction.getTaskController().editTask(task, sensors, actions);
                        this.guiAction.sendGuiAction(GuiAction.AC.CHANGE_MENU, GuiAction.AC.CHANGE_LAYOUT_TASKS, null, null);
                    }
                }

                if(success)
                    doInit();
            }
        } catch(Exception ex) {
            success = false;
            this.guiAction.showMessage(ex.getMessage());
        }

        return success;
    }

    public void removeAction(View view) throws Exception {
        if(viewActionSelectionLayout == view.getParent())
            viewActionSelectionLayout.removeView(view);
    }

    public void editTask(View view) {
        if(view.getTag() instanceof Task) {
            Task task = (Task)view.getTag();

            this.editTextTaskName.setText(task.getName());

            List<Sensor> taskSensors = task.getSensors();

            for(Sensor taskSensor : taskSensors) {
                for(int i=0; i<viewTriggerLayout.getChildCount(); i++) {
                    Sensor sensor = (Sensor)viewTriggerLayout.getChildAt(i).getTag();

                    if(sensor.getClass().getName().equals(taskSensor.getClass().getName()))
                        sensor.setDialogInputParameter(taskSensor.getInputParameters());
                }
            }

            List<Action> taskActions = task.getActions();
            generateSelectionView(viewActionSelection, viewActionSelectionLayout, taskActions.toArray(new SelectionViewItem[taskActions.size()]), this, false);
        }
    }

    private List<Sensor> getSelectedSensors() {
        List<Sensor> sensors = new ArrayList<>();

        for(int i=0; i<viewTriggerLayout.getChildCount(); i++) {
            Sensor sensor = (Sensor)viewTriggerLayout.getChildAt(i).getTag();

            if(sensor.hasSelection())
                sensors.add(sensor);
        }

        return sensors;
    }

    private List<Action> getSelectedActions() {
        List<Action> actions = new ArrayList<>();

        for(int i=0; i<viewActionSelectionLayout.getChildCount(); i++) {
            Action action = (Action)viewActionSelectionLayout.getChildAt(i).getTag();

            if(action.hasSelection());
                actions.add(action);
        }

        return actions;
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
