package com.spif.gui;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spif.opentaskworker.R;

import java.util.TreeMap;

public class ContentHandler {
    private static ContentHandler contentHandler = null;
    private static GuiAction guiAction = null;
    private ContentElement currentContentElement = null;

    private TreeMap<GuiAction.AC, ContentElement> contents = null;

    public static ContentHandler getInstance() {
        return contentHandler;
    }

    public static ContentHandler createInstance(GuiAction _guiAction) {
        if(contentHandler == null)
            contentHandler = new ContentHandler(_guiAction);

        return contentHandler;
    }

    private ContentHandler(GuiAction _guiAction) {
        guiAction = _guiAction;

        this.contents = new TreeMap<>();
        this.contents.put(GuiAction.AC.CHANGE_LAYOUT_DASHBOARD, new ContentDashboard((LinearLayout)guiAction.getActivity().findViewById(R.id.layoutDashboard)));
        this.contents.put(GuiAction.AC.CHANGE_LAYOUT_TASKS, new ContentTasks((LinearLayout)guiAction.getActivity().findViewById(R.id.layoutTasks)));
        this.contents.put(GuiAction.AC.CHANGE_LAYOUT_NEW, new ContentNew((LinearLayout)guiAction.getActivity().findViewById(R.id.layoutNew)));
        this.contents.put(GuiAction.AC.CHANGE_LAYOUT_SETTINGS, new ContentSettings((LinearLayout)guiAction.getActivity().findViewById(R.id.layoutSettings)));

        currentContentElement = contents.values().iterator().next();
    }

    public ContentElement changeLayout(GuiAction.AC layoutAC) {
        if(contents.containsKey(layoutAC))
        {
            for(ContentElement content : contents.values())
                content.getLayout().setVisibility(View.INVISIBLE);

            contents.get(layoutAC).getLayout().setVisibility(View.VISIBLE);
            currentContentElement = contents.get(layoutAC);
        }

        return currentContentElement;
    }

    public ContentElement getCurrentContentElement() {
        return this.currentContentElement;
    }
}
