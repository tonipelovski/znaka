package com.znaka.StdLib;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.NativeFunction;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.ExitException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Library {

    public static void addFunctions(HashSet<Function> functions){
        functions.add(printFn());
        functions.add(pow());
        functions.add(sqrt());
        functions.add(floor());
        functions.add(ceil());
        functions.add(exit());

    }

    private static NativeFunction printFn(){

        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("str", new DataVal<>("", "string"), false));
        return new NativeFunction("println", "void", args) {
            @Override
            public DataVal call(List<DataVal> arguments) {
                System.out.println(arguments.get(0));
                return null;
            }

        };
    }

    private static NativeFunction exit(){

        List<Variable> args = new ArrayList<>();
        return new NativeFunction("exit", "void", args) {
            @Override
            public DataVal call(List<DataVal> arguments) throws ExitException {
                throw new ExitException();
            }

        };
    }

    private static Function pow(){
        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("base", new DataVal<>("", "double"), false));
        args.add(new Variable<>("arg", new DataVal<>("", "double"), false));
        return new NativeFunction("pow", "double", args) {
            @Override
            public DataVal call(List<DataVal> arguments) {
                double result = Math.pow((double) arguments.get(0).getVal(), (double) arguments.get(1).getVal());
                return new DataVal<>(result, "double");
            }

        };
    }

    private static Function sqrt(){
        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("base", new DataVal<>("", "double"), false));
        return new NativeFunction("sqrt", "double", args) {
            @Override
            public DataVal call(List<DataVal> arguments) {
                double result = Math.sqrt((double) arguments.get(0).getVal());
                return new DataVal<>(result, "double");
            }

        };
    }

    private static Function floor(){
        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("base", new DataVal<>("", "double"), false));
        return new NativeFunction("floor", "double", args) {
            @Override
            public DataVal call(List<DataVal> arguments) {
                double result = Math.floor((double) arguments.get(0).getVal());
                return new DataVal<>(result, "double");
            }

        };
    }

    private static Function ceil(){
        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("base", new DataVal<>("", "double"), false));
        return new NativeFunction("ceil", "double", args) {
            @Override
            public DataVal call(List<DataVal> arguments) {
                double result = Math.ceil((double) arguments.get(0).getVal());
                return new DataVal<>(result, "double");
            }

        };
    }


}
