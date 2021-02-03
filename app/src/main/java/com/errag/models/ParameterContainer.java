package com.errag.models;

import java.util.TreeMap;

public class ParameterContainer {
    private TreeMap<String, Parameter> parameters = null;

    public ParameterContainer(Parameter[] _parameters) {
        this.parameters = new TreeMap<>();

        for(Parameter _parameter : _parameters)
            this.parameters.put(_parameter.getValue(), _parameter);
    }

    public String getString(String key) {
        if(!parameters.containsKey(key))
            return null;

        return parameters.get(key).getInput();
    }

    public Integer getInteger(String key) {
        String parameter = getString(key);

        if(parameter == null || parameter.length() == 0)
            return null;

        return Integer.parseInt(parameter);
    }

    public Boolean getBoolean(String key) {
        String parameter = getString(key);

        if(parameter == null)
            return null;

        return Boolean.parseBoolean(parameter);
    }

    public boolean testValue(String key, String value) {
        String parameterValue = null;

        if(parameters.containsKey(key))
            parameterValue = parameters.get(key).getInput();

        return (parameterValue != null && value != null && (parameterValue.trim().length() == 0 || parameterValue.toUpperCase().equals(value.toLowerCase())));
    }

    public boolean testValue(String key, Integer value) {
        return testValue(key, value.toString());
    }

    public boolean testValue(String key, Boolean value) {
        return testValue(key, value.toString());
    }
}
