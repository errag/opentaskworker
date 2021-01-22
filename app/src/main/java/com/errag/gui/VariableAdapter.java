package com.errag.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.errag.models.Variable;
import com.errag.opentaskworker.R;

import java.util.ArrayList;

public class VariableAdapter extends ArrayAdapter<Variable> {
    private ArrayList<Variable> items = null;
    private Context context = null;

    public VariableAdapter(ArrayList<Variable> _items, Context _context) {
        super(_context, R.layout.variable_item, _items);
        this.items = _items;
        this.context = _context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Variable variable = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(R.layout.variable_item, parent, false);

        String name = variable.getVariableName();
        String value = variable.getInputParameters()[variable.getInputParameters().length - 1].getInput();

        if(value != null && value.length() > 0)
            name += " = " + value;

        ((TextView)convertView.findViewById(R.id.variable_item_text)).setText(name);
        ((ImageView)convertView.findViewById(R.id.variable_item_image)).setImageResource(variable.getImage());

        return convertView;
    }
}
