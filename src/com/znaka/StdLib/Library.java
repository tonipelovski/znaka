package com.znaka.StdLib;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.NativeFunction;
import com.znaka.EvaluatorStructures.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Library {

    public static void addFunctions(HashSet<Function> functions){
        functions.add(printFn());
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

    private static Function pow(){
        return null;
    }

}
