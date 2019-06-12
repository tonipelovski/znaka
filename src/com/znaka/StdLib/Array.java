package com.znaka.StdLib;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.NativeFunction;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.ArgumentException;
import com.znaka.Exceptions.WrongType;

import java.util.ArrayList;
import java.util.List;

public class Array {
    public static NativeFunction add() {
        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("self", new DataVal<>("", "array"), true));
        args.add(new Variable<>("el", new DataVal<>("", "any"), true));
        return new NativeFunction("add", "void", args) {
            @Override
            public DataVal call(List<DataVal> arguments) throws ArgumentException, WrongType {
//                FunctionCall.validateAllArgs(arguments, args);
                System.out.println("running add function");
                return null;
            }

        };
    }
}
