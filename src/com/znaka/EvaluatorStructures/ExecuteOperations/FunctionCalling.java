package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.NoSuchFunction;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.FunctionCallAST;

import java.util.ArrayList;
import java.util.List;

public class FunctionCalling extends BaseExecuteOper {
    public FunctionCalling(Evaluator eval) {
        super(FunctionCallAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException {
        FunctionCallAST fnc = (FunctionCallAST)ast;
        Function f = getEvaluator().getFunctions().stream()
                .filter(o -> o.getName().equals(fnc.getText()))
                .findFirst()
                .orElse(null);
        if(f == null){
            throw new NoSuchFunction("No such function: " + fnc.getText());
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
