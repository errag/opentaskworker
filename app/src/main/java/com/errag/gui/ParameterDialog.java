package com.errag.gui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aditya.filebrowser.Constants;
import com.aditya.filebrowser.FileChooser;
import com.aditya.filebrowser.FolderChooser;
import com.errag.models.Action;
import com.errag.models.Parameter;
import com.errag.models.ParameterContainer;
import com.errag.models.SelectionViewItem;
import com.errag.models.Variable;
import com.errag.opentaskworker.MainActivity;
import com.errag.opentaskworker.R;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ParameterDialog extends Dialog implements View.OnClickListener {
    enum Close {
        SAVE,
        DELETE,
        CLOSE,
        TEST
    }

    private final static int THEME = R.layout.dialog_parameter;

    private GuiAction guiAction = null;
    private Parameter[] parameters = null;
    private View view = null;
    private SelectionViewItem selectionViewItem = null;
    private Boolean readonly = false;
    private View activityListener = null;

    private static ArrayList<ParameterDialog> instances = new ArrayList<>();

    public ParameterDialog(GuiAction _guiAction, View _view) {
        this(_guiAction, _view, false);
    }

    public ParameterDialog(GuiAction _guiAction, View _view, boolean _readonly) {
        this(_guiAction.getActivity());
        this.guiAction = _guiAction;
        this.view = _view;
        this.selectionViewItem = (SelectionViewItem)this.view.getTag();
        this.selectionViewItem.askForPermissions(this.guiAction.getActivity());
        this.parameters = this.selectionViewItem.getInputParameters();
        this.readonly = _readonly;
        this.activityListener = null;

        this.setCanceledOnTouchOutside(false);

        instances.add(this);
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

        if(instances.contains(this))
            instances.remove(this);

        if(this.view != null)
            this.guiAction.sendGuiAction(GuiAction.AC.DIALOG_CLOSE, AC, this.view, null, null);
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
            } else if(type.equals(Parameter.Type.STRING) || type.equals(Parameter.Type.INTEGER)
                    || type.equals(Parameter.Type.DATE) || type.equals(Parameter.Type.TIME)
                    || type.equals(Parameter.Type.PASSWORD) || type.equals(Parameter.Type.DIRECTORY)){
                header = getHeader(text);
                append = getEditText(parameter);
            } else if(type.equals(Parameter.Type.DROPDOWN)) {
                header = getHeader(text);
                append = getDropDown(parameter);
            }

            if(append != null) {
                if(header != null)
                    layout.addView(header);

                append.setEnabled(!readonly);
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
                        if(layout.getChildAt(i) instanceof CheckBox) {
                            CheckBox child = (CheckBox) layout.getChildAt(i);

                            if (child != checkbox && child.isChecked())
                                child.setChecked(false);
                        }
                    }
                }
            }
        });

        return checkbox;
    }

    private EditText getEditText(Parameter parameter) {
        final float scale = this.getContext().getResources().getDisplayMetrics().density;
        boolean isNumber = parameter.getType().equals(Parameter.Type.INTEGER);
        boolean isPassword = parameter.getType().equals(Parameter.Type.PASSWORD);
        boolean isDate = parameter.getType().equals(Parameter.Type.DATE);
        boolean isTime = parameter.getType().equals(Parameter.Type.TIME);
        boolean isDirectory = parameter.getType().equals(Parameter.Type.DIRECTORY);

        if(parameter.getInput() == null)
            parameter.setInput("");

        final EditText editText = new EditText(this.getContext());
        editText.setWidth((int) (250 * scale + 0.5f));
        editText.setText(parameter.getInput());
        editText.setInputType(
                isNumber ? InputType.TYPE_CLASS_NUMBER :
                        isPassword ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD:
                                InputType.TYPE_CLASS_TEXT
        );

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

        if(isDate)
            setEditTextToDatePicker(editText);
        else if(isTime)
            setEditTextToTimePicker(editText);
        else if(isDirectory)
            setEditTextToDirectory(editText);
        ;
        return editText;
    }

    private Spinner getDropDown(Parameter parameter) {
        final Spinner spinner = new Spinner(this.getContext());
        ArrayList<String> spinnerData = new ArrayList<>();
        String parameterData = parameter.getData();

        spinnerData.add("");

        if(parameterData != null) {
            for (String data : parameterData.split("[;]")) {
                spinnerData.add(data);
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerData);
        spinner.setAdapter(spinnerAdapter);
        spinner.setTag(parameter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Parameter item = (Parameter)spinner.getTag();

                if(item != null) {
                    String value = spinner.getAdapter().getItem(position).toString();
                    item.setInput(value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return spinner;
    }

    private TextView getHeader(String header) {
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 25, 0, 0);

        final TextView textView = new TextView(this.getContext());
        textView.setText(header + ":");
        textView.setLayoutParams(params);

        return textView;
    }

    private void setEditTextToDatePicker(final EditText editText) {
        editText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

            final DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
                Calendar sCalendar = Calendar.getInstance(TimeZone.getDefault());
                sCalendar.set(Calendar.YEAR, year);
                sCalendar.set(Calendar.MONTH, month);
                sCalendar.set(Calendar.DATE, dayOfMonth);

                editText.setText(new SimpleDateFormat("yyyy-MM-dd").format(sCalendar.getTime()));
            };

            DatePickerDialog dialog = new DatePickerDialog(guiAction.getActivity(), listener,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
        });
    }

    private void setEditTextToTimePicker(final EditText editText) {
        editText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

            final TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Calendar sCalendar = Calendar.getInstance(TimeZone.getDefault());
                    sCalendar.set(Calendar.HOUR, hourOfDay);
                    sCalendar.set(Calendar.MINUTE, minute);

                    editText.setText(new SimpleDateFormat("hh:mm a").format(sCalendar.getTime()));
                }
            };

            TimePickerDialog dialog = new TimePickerDialog(guiAction.getActivity(), listener,
                    calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);

            dialog.show();
        });
    }

    private void setEditTextToDirectory(final EditText editText) {
        editText.setOnClickListener(v -> {
            this.activityListener = editText;
            // thanks to
            // https://android-arsenal.com/details/1/5636


            Intent intent = new Intent(getContext(), FolderChooser.class);
            intent.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
            intent.putExtra(Constants.INITIAL_DIRECTORY, new File("content://org.nextcloud.documents/document/-2112235573%2F2").getAbsolutePath());
            guiAction.getActivity().startActivityForResult(intent, MainActivity.REQUEST_DIRECTORY);
        });
    }

    // listeners
    private void initListeners() {
        Button buttonSave = (Button)this.findViewById(R.id.btnParameterSave);
        Button buttonDelete = (Button)this.findViewById(R.id.btnParameterDelete);
        Button buttonTest = (Button)this.findViewById(R.id.btnParameterTest);
        Button buttonClose = (Button)this.findViewById(R.id.btnParameterClose);

        buttonSave.setOnClickListener(onClickSave());
        buttonDelete.setOnClickListener(onClickDelete());
        buttonTest.setOnClickListener(onClickTest());
        buttonClose.setOnClickListener(onClickClose());

        if(this.selectionViewItem instanceof Action && !this.readonly)
            buttonTest.setVisibility(View.VISIBLE);
        else
            buttonTest.setVisibility(View.GONE);

        // readonly?
        int visible = (!this.readonly ? View.VISIBLE : View.GONE);
        int invisible = (this.readonly ? View.VISIBLE : View.GONE);

        buttonSave.setVisibility(visible);
        buttonDelete.setVisibility(visible);
        buttonClose.setVisibility(invisible);
    }

    private View.OnClickListener onClickSave() {
        return v -> closeDialog(Close.SAVE);
    }

    private View.OnClickListener onClickClose() {
        return v -> closeDialog(Close.CLOSE);
    }

    private View.OnClickListener onClickDelete() {
        return v -> {
            clearDialogParamterItem();
            closeDialog(Close.DELETE);
        };
    }

    private View.OnClickListener onClickTest() {
        return v -> {
            try {
                if (selectionViewItem instanceof Action) {
                    Action action = (Action) selectionViewItem;
                    action.exec(guiAction.getActivity(), new ParameterContainer(selectionViewItem.getInputParameters()));
                }
            } catch(Exception ex) {
                // TODO error-handling
                ex.printStackTrace();
            }
        };
    }

    public void sendToActivityListenerView(String message) {
        if(this.activityListener != null) {
            if(this.activityListener instanceof EditText)
                ((EditText)this.activityListener).setText(message);
        }
    }

    private void setActivityListenerView(View view) {
        this.activityListener = view;
    }

    @Override
    public void onClick(View v) {

    }

    // static methods
    public static ParameterDialog getMainDialog() {
        ParameterDialog dialog = null;

        if(instances.size() > 0)
            dialog = instances.get(0);

        return dialog;
    }
}
