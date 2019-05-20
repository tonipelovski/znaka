package com.znaka.EvaluatorStructures.Functions;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Scope;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.ArgumentException;
import com.znaka.Exceptions.WrongType;

import java.util.List;
import java.util.Objects;

public class FunctionCall {
    private Function func;
    private Scope scope;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionCall that = (FunctionCall) o;
        return func.equals(that.func) &&
                scope.equals(that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(func, scope);
    }

    public Scope getScope() {
        return scope;
    }

    public Function getFunc() {
        return func;
    }


    public static void validateAllArgs(List<DataVal> arguments, List<Variable> args) throws ArgumentException, WrongType {
        if(args.size() < arguments.size()){
            throw new ArgumentException("Too few arguments provided");
        }
        else if(args.size() > arguments.size()){
            throw new ArgumentException("Too much arguments provided");
        }
        for (int i = 0; i < args.size(); i++) {
            Variable.validateType(args.get(i).getVal(), arguments.get(i));
        }
    }

    private void createVariablesIntoScope(List<DataVal> args) {
        for (int i = 0; i < func.getArgs().size(); i++) {
            Variable var = func.getArgs().get(i);
            scope.variables.add(new Variable(var.getName(), args.get(i), var.isConst()));
        }
    }

    public FunctionCall(Function func, List<DataVal> args) throws ArgumentException, WrongType {
        this.func = func;
        this.scope = new Scope();
        validateAllArgs( args, func.getArgs());
        createVariablesIntoScope(args);


    }
}
