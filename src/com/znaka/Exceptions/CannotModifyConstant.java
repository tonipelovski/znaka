package com.znaka.Exceptions;

public class CannotModifyConstant extends Throwable {
    @Override
    public String getMessage() {
        return "Cannot change value of constant: ";
    }
}
