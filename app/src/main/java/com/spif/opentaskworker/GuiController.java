package com.spif.opentaskworker;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.spif.gui.ContentElement;
import com.spif.gui.ContentHandler;
import com.spif.gui.ContentNew;
import com.spif.gui.GuiAction;
import com.spif.gui.HeaderMenu;

public class GuiController implements GuiAction {
    private Activity activity = null;

    private HeaderMenu headerMenu = null;
    private ContentHandler contentHandler = null;
    private TaskController taskController = null;

    public GuiController(Activity _activity, TaskController _taskController)
    {
        this.activity = _activity;
        this.headerMenu = HeaderMenu.createInstance(this);
        this.contentHandler = ContentHandler.createInstance(this);
        this.taskController = _taskController;
    }

    private void changeHeaderMenu(View target, AC layoutAC) {
        Button button = (Button)target;

        this.headerMenu.changeTextMenu(button.getText(), button.getBackgroundTintList().getDefaultColor());
        ContentElement contentElement = this.contentHandler.changeLayout(layoutAC);

        if(!contentElement.isInit())
            contentElement.init(this);
    }

    private void refreshCurrentPanel(AC parameter, View view) {
        try {
            ContentElement currentContent = this.contentHandler.getCurrentContentElement();

            if (parameter.equals(AC.NEW_ACTION))
                ((ContentNew)currentContent).addAction(view);
            else if(parameter.equals(AC.NEW_ACTION_DELETE))
                ((ContentNew)currentContent).removeAction(view);

            currentContent.updateUI();
        } catch(Exception ex) {
            // TODO error-handline
            ex.printStackTrace();
        }
    }

    @Override
    public void sendGuiAction(AC action, AC parameter, View target, View.OnClickListener source)
    {
        if(action.equals(AC.CHANGE_MENU))
            this.changeHeaderMenu(target, parameter);
        else if(action.equals(AC.DIALOG_CLOSE))
            this.refreshCurrentPanel(parameter, target);
    }

    @Override
    public Activity getActivity() {
        return this.activity;
    }

    @Override
    public TaskController getTaskController() { return this.taskController; }
}
