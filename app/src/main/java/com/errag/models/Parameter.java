package com.errag.models;

public class Parameter {
    public enum Type {
        BOOLEAN,
        RADIO,
        INTEGER,
        STRING,
        PASSWORD,
        DATE,
        TIME,
        DIRECTORY,
        DROPDOWN
    }

    private Integer text = null;
    private String value = null;
    private Type type = null;
    private String input = null;
    private String data = null;

    public Parameter(Integer _text, String _value, Type _type) {
        this.text = _text;
        this.value = _value;
        this.type = _type;
    }

    public Parameter(Integer _text, String _value, Type _type, String _data) {
        this(_text, _value, _type);
        this.data = _data;
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

    public String getInput() { return input; }

    public void setInput(String input) {
        this.input = input;
    }

    public void setInput(Boolean input) {
        this.setInput(String.valueOf(input));
    }

    public void setInput(Integer input) {
        this.setInput(String.valueOf(input));
    }

    public String getData() {
        return this.data;
    }
}
