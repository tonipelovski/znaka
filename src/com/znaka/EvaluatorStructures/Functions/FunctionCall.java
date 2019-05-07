package com.znaka.EvaluatorStructures.Functions;

import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Scope;
import com.znaka.Exceptions.ArgumentException;
import com.znaka.Exceptions.WrongType;

import java.util.List;

public class FunctionCall {
    private Function func;
    private Scope scope;

    private void validateArgs(List<DataVal> args) throws ArgumentException, WrongType{
        if(args.size() < func.getArgs().size()){
            throw new ArgumentException("Too few arguments provided");
        }
        else if(args.size() > func.getArgs().size()){
            throw new ArgumentException("Too much arguments provided");
        }
        for (int i = 0; i < args.size(); i++) {
            if(!args.get(i).getType().equals(func.getArgs().get(i).getVal().getType())){
                throw new WrongType(args.get(i).getType(),
                        func.getArgs().get(i).getVal().getType(), args.get(i).getVal().toString());
            }
        }
    }

    public FunctionCall(Function func, List<DataVal> args) throws ArgumentException, WrongType {
        this.func = func;
        validateArgs(args);

    }

    public void call(){

    }
}
