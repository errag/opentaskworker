package com.errag.gui;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.errag.models.SelectionViewItem;
import com.errag.models.Sensor;
import com.errag.models.Task;
import com.errag.opentaskworker.R;

import java.util.List;

public class ContentTasks extends ContentElement {
    public ContentTasks(LinearLayout _layout) {
        super(_layout);
    }

    LinearLayout linearLayoutTasks = null;

    @Override
    protected void doInit() {
        if(!isInit) {
            this.linearLayoutTasks = this.guiAction.getActivity().findViewById(R.id.layoutTasks);
        }
    }

    private void refreshTasks() {
        List<Task> tasks = this.guiAction.getTaskController().getTasks();

        this.linearLayoutTasks.removeAllViews();

        for(Task task : tasks) {
            // getter
            View view = LayoutInflater.from(this.guiAction.getActivity()).inflate(R.layout.task_item, null);
            CheckBox taskActive = (CheckBox)view.findViewById(R.id.task_active);
            Button taskEdit = (Button)view.findViewById(R.id.button_task_edit);
            Button taskDelete = (Button)view.findViewById(R.id.button_task_delete);

            // tag views
            taskActive.setTag(task);
            taskEdit.setTag(task);
            taskDelete.setTag(task);

            // setter
            taskActive.setChecked(task.isActive());
            taskActive.setOnCheckedChangeListener(getOnChangeTaskActive());
            taskActive.setText(task.getName());

            // button listener
            View.OnClickListener buttonListener = getOnClickListenerTask(taskEdit, taskDelete, task);
            taskEdit.setOnClickListener(buttonListener);
            taskDelete.setOnClickListener(buttonListener);

            // content
            HorizontalScrollView scrollView = (HorizontalScrollView)view.findViewById(R.id.task_content);
            LinearLayout layout = (LinearLayout)scrollView.getChildAt(0);
            SelectionViewItem[] items = task.getSelectionViewItems();

            this.generateSelectionView(scrollView, layout, items, getOnClickListenerItems(), false);
            this.linearLayoutTasks.addView(view);
        }
    }

    private CompoundButton.OnCheckedChangeListener getOnChangeTaskActive() {
        return (buttonView, isChecked) -> {
            Task task = (Task)buttonView.getTag();
            task.setActive(isChecked);

            guiAction.getTaskController().saveTasks();
        };
    }

    private View.OnClickListener getOnClickListenerItems() {
        return v -> {
            ParameterDialog parameterDialog = new ParameterDialog(this.guiAction, v, true);
            parameterDialog.show();
        };
    }

    private View.OnClickListener getOnClickListenerTask(final Button taskEdit, final Button taskDelete, final Task task) {
        return v -> {
            if(v == taskEdit)
                guiAction.sendGuiAction(GuiAction.AC.TASK_EDIT, GuiAction.AC.EDIT_EXISTING_TAGS, v, null, null);
            else if(v == taskDelete)
            {
                openConfirmDialog(
                    guiAction.getActivity().getString(R.string.alert_warning),
                    guiAction.getActivity().getString(R.string.alert_delete_task),
                    (dialog, which) -> {
                        guiAction.getTaskController().removeTask(task);
                        refreshTasks();
                    }
                );
            }
        };
    }

    @Override
    public void updateUI() {
        refreshTasks();
    }

    @Override
    public MODULE getModule() {
        return MODULE.TASKS;
    }
}
