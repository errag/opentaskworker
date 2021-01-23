package com.errag.models;

import androidx.annotation.NonNull;

import com.errag.opentaskworker.R;

import java.util.TreeMap;
import java.util.TreeSet;

public abstract class Variable extends SelectionViewItem {
    @Override
    public void initInputParameters() {
        super.initInputParameters();

        // add input-field for variablename
        Parameter[] temp = new Parameter[this.inputParameter.length + 1];
        temp[0] = new Parameter(R.string.variable_name, VARIABLE.NAME.toString(), Parameter.Type.STRING);
        System.arraycopy(this.inputParameter, 0, temp, 1, this.inputParameter.length);

        this.inputParameter = temp;
    }

    // static methods
    public static Variable createByMove(Variable _variable) throws Exception {
        Class<?> classClone = Class.forName(_variable.getClass().getName());
        Variable variableClone = (Variable)classClone.newInstance();
        variableClone.changeInputParameter(_variable.getInputParameters());
        _variable.resetInputParameter();

        return variableClone;
    }

    public String getVariableName() {
        return Action.getACString(VARIABLE.NAME.toString(), this.inputParameter);
    }

    public enum DATE {
        VALUE;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum EMAIL {
        SERVER,
        PORT,
        USE_AUTH,
        USERNAME,
        PASSWORD;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum NUMBER {
        VALUE;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum PASSWORD {
        VALUE;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum STRING {
        VALUE;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum TIME {
        VALUE;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }

    public enum VARIABLE {
        NAME;

        @NonNull
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "." + this.name();
        }
    }
}
