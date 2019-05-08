package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Contracts.FunctionCallASTInter;
import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.NoSuchFunction;
import com.znaka.ParserStructures.DefaultAST;

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
            args.add(getEvaluator().Eval(arg));
            // evaluate and add each
        }
        FunctionCall call = new FunctionCall(f, args);
        getEvaluator().getCallStack().push(call);
        getEvaluator().switchScope();
        for (DefaultAST line : f.getBody()) {
            getEvaluator().ExecLine(line);
        }
        DataVal returned = getEvaluator().getLastReturnedValue();
        validateReturnType(returned);
        getEvaluator().getCallStack().pop();
        getEvaluator().switchScope();
        return returned;
    }

    private void validateReturnType(DataVal lastReturnedValue) {

    }
}
