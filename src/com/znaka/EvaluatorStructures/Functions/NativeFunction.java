package com.znaka.EvaluatorStructures.Functions;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.ArgumentException;
import com.znaka.Exceptions.ExitException;
import com.znaka.Exceptions.WrongType;

import java.util.List;

public abstract class NativeFunction extends Function {
    public NativeFunction(String name, String return_type, List<Variable> args) {
        super(name, return_type, args, null);
    }

    public abstract DataVal call(List<DataVal> arguments) throws ExitException, ArgumentException, WrongType;
}
