package com.errag.gui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.SelectionViewItem;
import com.errag.models.Variable;
import com.errag.opentaskworker.R;

public class ParameterDialog extends Dialog implements View.OnClickListener {
    enum Close {
        SAVE,
        DELETE,
        TEST
    }

    private final static int THEME = R.layout.dialog_parameter;

    private GuiAction guiAction = null;
    private Parameter[] parameters = null;
    private View view = null;
    private SelectionViewItem selectionViewItem = null;

    public ParameterDialog(GuiAction _guiAction, View _view) {
        this(_guiAction.getActivity());
        this.guiAction = _guiAction;
        this.view = _view;
        this.selectionViewItem = (SelectionViewItem)this.view.getTag();
        this.selectionViewItem.askForPermissions(this.guiAction.getActivity());
        this.parameters = this.selectionViewItem.getInputParameters();

        this.setCanceledOnTouchOutside(false);
    }

    public ParameterDialog(@NonNull Context context) {
        super(context);
    }

    public ParameterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ParameterDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(THEME);

        initDialog();
        initListeners();
    }

    private void clearDialogParamterItem() {
        this.selectionViewItem.resetInputParameter();
    }

    public void closeDialog(Close mode) {
        GuiAction.AC AC = GuiAction.AC.NEW_TRIGGER;

        if(this.selectionViewItem instanceof Action) {
            if(mode.equals(Close.SAVE))
                AC = GuiAction.AC.NEW_ACTION;
            else
                AC = GuiAction.AC.NEW_ACTION_DELETE;
        } else if(this.selectionViewItem instanceof Variable) {
            if(mode.equals(Close.SAVE))
                AC = GuiAction.AC.SETTING_NEW_VARIABLE;
            else
                AC = GuiAction.AC.SETTING_DELETE_VARIABLE;
        }

        this.dismiss();

        if(this.view != null)
            this.guiAction.sendGuiAction(GuiAction.AC.DIALOG_CLOSE, AC, this.view, null);
    }

    private void initDialog() {
        LinearLayout layout = this.findViewById(R.id.containerParameter);
        layout.removeAllViews();

        for(Parameter parameter : this.parameters) {
            String text = this.getContext().getString(parameter.getText());
            Parameter.Type type = parameter.getType();
            View header = null;
            View append = null;

            if(type.equals(Parameter.Type.BOOLEAN) || type.equals(Parameter.Type.RADIO)) {
                append = getCheckBox(parameter);
            } else if(type.equals(Parameter.Type.STRING) || type.equals(Parameter.Type.INTEGER)) {
                header = getHeader(text);
                append = getEditText(parameter);
            }

            if(append != null) {
                if(header != null)
                    layout.addView(header);

                append.setTag(parameter);
                layout.addView(append);
            }

        }
    }

    // getter view
    private CheckBox getCheckBox(Parameter parameter) {
        if(parameter.getInput() == null)
            parameter.setInput(false);

        final CheckBox checkbox = new CheckBox(this.getContext());
        checkbox.setText(parameter.getText());
        checkbox.setChecked(Boolean.parseBoolean(parameter.getInput()));
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Parameter parameter = (Parameter)checkbox.getTag();
                parameter.setInput(isChecked);
                buttonView.setTag(parameter);

                // uncheck other checkboxes (dummy-radiobutton)
                if(parameter.getType().equals(Parameter.Type.RADIO) && isChecked)
                {
                    LinearLayout layout = (LinearLayout)buttonView.getParent();

                    for(int i=0; i<layout.getChildCount(); i++) {
                        CheckBox child = (CheckBox)layout.getChildAt(i);

                        if(child != checkbox && child.isChecked())
                            child.setChecked(false);
                    }
                }
            }
        });

        return checkbox;
    }

    private EditText getEditText(Parameter parameter) {
        final float scale = this.getContext().getResources().getDisplayMetrics().density;
        boolean isNumber = parameter.getType().equals(Parameter.Type.INTEGER);

        if(parameter.getInput() == null)
            parameter.setInput("");

        final EditText editText = new EditText(this.getContext());
        editText.setWidth((int) (250 * scale + 0.5f));
        editText.setText(parameter.getInput());
        editText.setInputType(isNumber ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Parameter parameter = (Parameter)editText.getTag();
                parameter.setInput(s.toString());
                editText.setTag(parameter);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        return editText;
    }

    private TextView getHeader(String header) {
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 25, 0, 0);

        final TextView textView = new TextView(this.getContext());
        textView.setText(header + ":");
        textView.setLayoutParams(params);

        return textView;
    }


    // listeners
    private void initListeners() {
        Button buttonSave = (Button)this.findViewById(R.id.btnParameterSave);
        Button buttonDelete = (Button)this.findViewById(R.id.btnParameterDelete);
        Button buttonTest = (Button)this.findViewById(R.id.btnParameterTest);

        buttonSave.setOnClickListener(onClickSave());
        buttonDelete.setOnClickListener(onClickDelete());
        buttonTest.setOnClickListener(onClickTest());

        if(this.selectionViewItem instanceof Action)
            buttonTest.setVisibility(View.VISIBLE);
        else
            buttonTest.setVisibility(View.GONE);
    }

    private View.OnClickListener onClickSave() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog(Close.SAVE);
            }
        };
    }

    private View.OnClickListener onClickDelete() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDialogParamterItem();
                closeDialog(Close.DELETE);
            }
        };
    }

    private View.OnClickListener onClickTest() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (selectionViewItem instanceof Action) {
                        Action action = (Action) selectionViewItem;
                        action.exec(guiAction.getActivity(), selectionViewItem.getInputParameters());
                    }
                } catch(Exception ex) {
                    // TODO error-handling
                    ex.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {

    }
}
