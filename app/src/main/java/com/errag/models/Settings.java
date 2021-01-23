package com.errag.models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Settings {
    List<Variable> variables = null;
    TreeMap<String, Variable> variablesDictionary = null;

    public Settings() {
        this(new ArrayList<>());
    }

    public Settings(List<Variable> _variables) {
        this.setVariables(_variables);
    }

    public Variable getVariableByName(String _name) {
        refreshVariableDictionary();

        return variablesDictionary.get(_name);
    }

    private void refreshVariableDictionary() {
        if(variables != null && (variablesDictionary == null || variablesDictionary.size() != variables.size())) {
            if(variablesDictionary == null)
                variablesDictionary = new TreeMap<>();
            else
                variablesDictionary.clear();

            for(Variable variable : this.getVariables())
                variablesDictionary.put(variable.getVariableName(), variable);
        }
    }


    public void setVariables(List<Variable> _variables) {
        this.variables = _variables;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void addVariable(Variable _variable) {
        this.getVariables().add(_variable);
    }

    public void removeVariable(Variable _variable) {
        this.variables.remove(_variable);
    }
}
