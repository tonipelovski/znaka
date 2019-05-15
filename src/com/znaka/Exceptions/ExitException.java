package com.znaka.Exceptions;

public class ExitException extends EvaluatorException {
    public ExitException() {
        super("Program exited.");
    }
}
