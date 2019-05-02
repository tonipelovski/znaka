package com.znaka.Exceptions;

public class WrongType extends EvaluatorException {
    public WrongType(String message) {
        super(message);
    }

    public WrongType(String type1, String type2, String wrong_val) {
        super(String.format("Cannot convert '%s'(%s) into '%s'", type1, wrong_val, type2));
    }
}
