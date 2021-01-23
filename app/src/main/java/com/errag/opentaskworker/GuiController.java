package com.errag.opentaskworker;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.errag.gui.ContentElement;
import com.errag.gui.ContentHandler;
import com.errag.gui.ContentNew;
import com.errag.gui.ContentSettings;
import com.errag.gui.GuiAction;
import com.errag.gui.HeaderMenu;
import com.errag.models.Task;

public class GuiController implements GuiAction {
    private Activity activity = null;

    private HeaderMenu headerMenu = null;
    private ContentHandler contentHandler = null;
    private TaskController taskController = null;

    private static GuiController guiController = null;

    private GuiController(Activity _activity, TaskController _taskController)
    {
        this.activity = _activity;
        this.headerMenu = HeaderMenu.createInstance(this);
        this.contentHandler = ContentHandler.createInstance(this);
        this.taskController = _taskController;
    }

    public static void initApp(Activity _activity, TaskController _taskController) {
        if(guiController == null)
            guiController = new GuiController(_activity, _taskController);
    }

    private void changeHeaderMenu(View target, AC layoutAC) {
        if(target != null && target instanceof Button) {
            Button button = (Button) target;
            this.headerMenu.changeTextMenu(button.getText(), button.getBackgroundTintList().getDefaultColor());
        }

        ContentElement contentElement = this.contentHandler.changeLayout(layoutAC);

        if(!contentElement.isInit())
            contentElement.init(this);

        contentElement.updateUI();
    }

    private void refreshCurrentPanel(AC parameter, View view) {
        try {
            ContentElement currentContent = this.contentHandler.getCurrentContentElement();

            if (parameter.equals(AC.NEW_ACTION))
                ((ContentNew)currentContent).addAction(view);
            else if(parameter.equals(AC.NEW_ACTION_DELETE))
                ((ContentNew)currentContent).removeAction(view);
            else if(parameter.equals(AC.EDIT_EXISTING_TAGS))
                ((ContentNew)currentContent).editTask(view);
            else if(parameter.equals(AC.SETTING_NEW_VARIABLE))
                ((ContentSettings)currentContent).addVariable(view);
            else if(parameter.equals(AC.SETTING_DELETE_VARIABLE))
                ((ContentSettings)currentContent).removeVariable(view);

            currentContent.updateUI();
        } catch(Exception ex) {
            // TODO error-handline
            ex.printStackTrace();
        }
    }

    private void editTask(View view, AC parameter) {
        if(view.getTag() instanceof Task) {
            this.changeHeaderMenu(null, AC.CHANGE_LAYOUT_NEW);
            this.refreshCurrentPanel(parameter, view);
        }
    }

    @Override
    public void sendGuiAction(AC action, AC parameter, View target, View.OnClickListener source)
    {
        if(action.equals(AC.CHANGE_MENU))
            this.changeHeaderMenu(target, parameter);
        else if(action.equals(AC.DIALOG_CLOSE))
            this.refreshCurrentPanel(parameter, target);
        else if(action.equals(AC.TASK_EDIT))
            this.editTask(target, parameter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public Activity getActivity() {
        return this.activity;
    }

    @Override
    public TaskController getTaskController() { return this.taskController; }
}
