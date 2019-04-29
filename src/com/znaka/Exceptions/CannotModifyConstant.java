package com.znaka.Exceptions;

public class CannotModifyConstant extends EvaluatorException {
    public CannotModifyConstant() {
        super("Cannot change value of constant: ");
    }

}
