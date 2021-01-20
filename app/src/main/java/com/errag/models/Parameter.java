package com.errag.models;

public class Parameter {
    public enum Type {
        BOOLEAN,
        INTEGER,
        PASSWORD,
        STRING
    }

    private Integer text = null;
    private String value = null;
    private Type type = null;
    private String input = null;

    public Parameter(Integer _text, String _value, Type _type) {
        this.text = _text;
        this.value = _value;
        this.type = _type;
    }

    public Integer getText() {
        return text;
    }

    public void setText(Integer text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setInput(Boolean input) {
        this.setInput(String.valueOf(input));
    }

    public void setInput(Integer input) {
        this.setInput(String.valueOf(input));
    }
}
