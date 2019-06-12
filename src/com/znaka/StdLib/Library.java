package com.znaka.StdLib;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.EvaluatorStructures.Functions.NativeFunction;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.ArgumentException;
import com.znaka.Exceptions.ExitException;
import com.znaka.Exceptions.WrongType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Library {

    public static void addFunctions(HashSet<Function> functions){
        /*functions.add(printFn());
        functions.add(pow());
        functions.add(sqrt());
        functions.add(floor());
        functions.add(ceil());
        functions.add(exit());
        functions.add(atof());
        functions.add(atoi());
        functions.add(rand());*/
        //GLEI SA KAK SE PRAWI
        Library l = new Library();
        Class<? extends Library> aClass = l.getClass();
        for (Method method : aClass.getDeclaredMethods()) {
            if(method.getReturnType().getSimpleName().equals("NativeFunction") ||
                    method.getReturnType().getSimpleName().equals("Function")){
                try {
                    functions.add((NativeFunction) method.invoke(Library.class));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private static NativeFunction printFn(){
        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("str", new DataVal<>("", "any"), false));
        return new NativeFunction("println", "void", args) {
            @Override
            public DataVal call(List<DataVal> arguments) throws ArgumentException, WrongType {
                FunctionCall.validateAllArgs(arguments, args);
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

    /*private static NativeFunction setDebug(){

        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("state", new DataVal<>("", "boolean"), false));
        return new NativeFunction("debugSet", "void", args) {
            @Override
            public DataVal call(List<DataVal> arguments) throws ExitException {

            }

        };
    }*/

    private static NativeFunction pow(){
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

    private static NativeFunction sqrt(){
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

    private static NativeFunction floor(){
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

    private static NativeFunction ceil(){
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

    private static NativeFunction rand(){
        List<Variable> args = new ArrayList<>();
        return new NativeFunction("rand", "double", args) {
            @Override
            public DataVal call(List<DataVal> arguments) {
                return new DataVal<>(Math.random(), "double");
            }

        };
    }

    private static NativeFunction atof(){
        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("str", new DataVal<>("", "string"), false));
        return new NativeFunction("atof", "float", args) {
            @Override
            public DataVal call(List<DataVal> arguments) {
                String arg = String.valueOf(arguments.get(0));
                arg = arg.substring(1, arg.length() - 1);
                return new DataVal<>(Float.parseFloat(arg), "float");
            }

        };
    }

    private static NativeFunction atoi(){
        List<Variable> args = new ArrayList<>();
        args.add(new Variable<>("str", new DataVal<>("", "string"), false));
        return new NativeFunction("atoi", "integer", args) {
            @Override
            public DataVal call(List<DataVal> arguments) {
                String arg = String.valueOf(arguments.get(0));
                arg = arg.substring(1, arg.length() - 1);

                return new DataVal<>(Integer.parseInt(arg), "integer");
            }

        };
    }


}
