package com.znaka.StdLib;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.NativeFunction;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.ArgumentException;
import com.znaka.Exceptions.WrongType;

import java.util.ArrayList;
import java.util.List;

public class Array extends Library{
    public static NativeFunction add() {
        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("self", new DataVal<>("", "array"), true));
        args.add(new Variable<>("el", new DataVal<>("", "any"), true));
        return new NativeFunction("add", "void", args) {
            @Override
            public DataVal call(List<DataVal> arguments) throws ArgumentException, WrongType {
//                FunctionCall.validateAllArgs(arguments, args);
                ((ArrayList) arguments.get(0).getVal()).add(arguments.get(1));

                System.out.println("running add function");
                return null;
            }

        };
    }

    public static NativeFunction get() {
        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("self", new DataVal<>("", "array"), true));
        args.add(new Variable<>("ind", new DataVal<>("", "integer"), true));
        return new NativeFunction("get", "any", args) {
            @Override
            public DataVal call(List<DataVal> arguments) throws ArgumentException, WrongType {
//                FunctionCall.validateAllArgs(arguments, args);
                DataVal res = (DataVal) ((ArrayList) arguments.get(0).getVal()).get((Integer) arguments.get(1).getVal());
                return res;
            }

        };
    }
}
