package com.spif.models;

import androidx.annotation.NonNull;

public enum BaseEnum {
    ;
    @NonNull
    @Override
    public String toString() {
        return this.getDeclaringClass().getName();
    }
}
