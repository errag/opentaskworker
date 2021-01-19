package com.spif.gui;

import android.content.ClipData;
import android.content.ClipDescription;
import android.text.Selection;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.spif.models.Action;
import com.spif.models.SelectionViewItem;
import com.spif.opentaskworker.R;

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

    private List<ImageButton> triggerButtons = null;

    private final static int TASK_NON_SELECTED = R.drawable.tasks_button;
    private final static int TASK_TRIGGER = R.drawable.tasks_button_trigger;
    private final static int TASK_ACTION = R.drawable.tasks_button_action;

    @Override
    protected void doInit() {
        viewTriggerList = (HorizontalScrollView)this.guiAction.getActivity().findViewById(R.id.scrollNewTriggerList);
        viewActionList = (HorizontalScrollView)this.guiAction.getActivity().findViewById(R.id.scrollNewActionList);
        viewActionSelection = (HorizontalScrollView)this.guiAction.getActivity().findViewById(R.id.scrollNewActionSelection);

        viewTriggerLayout = (LinearLayout)viewTriggerList.getChildAt(0);
        viewActionLayout = (LinearLayout)viewActionList.getChildAt(0);
        viewActionSelectionLayout = (LinearLayout)viewActionSelection.getChildAt(0);

        SelectionViewItem[] availableSensors = this.guiAction.getTaskController().getAvailableSensors();
        generateSelectionView(viewTriggerList, viewTriggerLayout, availableSensors);

        SelectionViewItem[] availableActions = this.guiAction.getTaskController().getAvailableActions();
        generateSelectionView(viewActionList, viewActionLayout, availableActions);

        generateSelectionView(viewActionSelection, viewActionSelectionLayout, null);

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
            if(event.getAction() == DragEvent.ACTION_DROP ) {
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

                View button = getSelectionViewButton(clone,true);
                button.setBackgroundResource(TASK_ACTION);
                this.viewActionSelectionLayout.addView(button);
            }
        }
    }

    public void removeAction(View view) throws Exception {
        if(viewActionSelectionLayout == view.getParent())
            viewActionSelectionLayout.removeView(view);
    }

    private void generateSelectionView(HorizontalScrollView scrollLayout, LinearLayout layout, SelectionViewItem[] items)
    {
        triggerButtons = new ArrayList<>();

        scrollLayout.setBackgroundResource(R.drawable.tasks_list);
        layout.setPadding(10, 20, 10, 20);

        if(items != null)
        {
            for(SelectionViewItem item : items) {
                item.resetInputParameter();
                ImageButton button = getSelectionViewButton(item, false);
                layout.addView(button);
            }
        }
    }

    public ImageButton getSelectionViewButton(SelectionViewItem item, boolean dragable) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 0, 10, 0);

        ImageButton button = new ImageButton(this.guiAction.getActivity());
        button.setTag(item);
        button.setLayoutParams(params);
        setImageButtonTriggerStyle(button, TASK_NON_SELECTED, TASK_TRIGGER);
        button.setImageResource(item.getImage());
        button.setAlpha((float)0.95);
        button.setOnClickListener(this);

        if(dragable) {
            button.setOnLongClickListener(v -> {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                return true;
            });
        }

        return button;
    }

    private void setImageButtonTriggerStyle(View button, int notSelected, int selected) {
        SelectionViewItem item = (SelectionViewItem)button.getTag();
        button.setBackgroundResource(!item.hasSelection() ? notSelected : selected);
    }

    @Override
    public void onClick(View v) {
        ParameterDialog parameterDialog = new ParameterDialog(this.guiAction, v);
        parameterDialog.show();
    }
}
