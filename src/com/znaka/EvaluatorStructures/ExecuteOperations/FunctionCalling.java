package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Contracts.FunctionCallASTInter;
import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.EvaluatorStructures.Functions.NativeFunction;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.NoSuchFunction;
import com.znaka.Exceptions.WrongType;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.ReturnAST;

import java.util.ArrayList;
import java.util.List;

public class FunctionCalling extends BaseExecuteOper {
    public FunctionCalling(Evaluator eval) {
        super(FunctionCallASTInter.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException {
        FunctionCallASTInter fnc = (FunctionCallASTInter) ast;
        Function f = getEvaluator().getFunctions().stream()
                .filter(o -> o.getName().equals(fnc.getName()))
                .findFirst()
                .orElse(null);
        if(f == null){
            throw new NoSuchFunction("No such function: " + fnc.getName());
        }
        List<DataVal> args = new ArrayList<>();
        for (DefaultAST arg : fnc.getArgs()) {
            if(arg == null){
                break;
            }
            args.add(getEvaluator().Eval(arg));
            if(arg instanceof ReturnAST){
                break;
            }
            // evaluate and add each
        }
        if(f instanceof NativeFunction){
            DataVal ret = ((NativeFunction) f).call(args);
            validateReturnType(ret, f);
            return ret;
        }
        FunctionCall call = new FunctionCall(f, args);
        call.getScope().functions.addAll(getEvaluator().getFunctions());
        getEvaluator().getCallStack().push(call);
        getEvaluator().switchScope();
        for (DefaultAST line : f.getBody()) {
            getEvaluator().ExecLine(line);
            if(line instanceof ReturnAST){
                break;
            }
        }
        DataVal returned = getEvaluator().getLastReturnedValue();
        validateReturnType(returned, f);
        getEvaluator().getCallStack().pop();
        getEvaluator().switchScope();
        return returned;
    }

    private void validateReturnType(DataVal lastReturnedValue, Function f) throws WrongType {
        if(!lastReturnedValue.getType().equals(f.getReturn_type())){
            throw new WrongType(lastReturnedValue.getType(), f.getReturn_type(), lastReturnedValue.toString());
        }
    }
}
