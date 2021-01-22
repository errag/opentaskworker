package com.errag.models;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    List<Variable> variables = null;

    public Settings() {
        this(new ArrayList<>());
    }

    public Settings(List<Variable> _variables) {
        this.setVariables(_variables);
    }

    public void setVariables(List<Variable> _variables) {
        this.variables = _variables;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void removeVariable(Variable _variable) {
        this.variables.remove(_variable);
    }
}
