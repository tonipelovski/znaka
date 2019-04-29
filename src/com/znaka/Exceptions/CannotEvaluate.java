package com.znaka.Exceptions;

public class CannotEvaluate extends Throwable {
    @Override
    public String getMessage() {
        return "Couldn't Evaluate expression";
    }
}
