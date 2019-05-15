package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Functions.FunctionCall;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.ReturnAST;

public class ReturnExecuteOper extends BaseExecuteOper {
    public ReturnExecuteOper(Evaluator eval) {
        super(ReturnAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException {
        ReturnAST ast1 = (ReturnAST)ast;

        DataVal returned = getEvaluator().Eval(ast1.getToReturn());
        FunctionCall call = getEvaluator().getCallStack().pop();
        Function f = call.getFunc();
        if(f.getReturn_type().equals("void")){
            returned = new DataVal<>(null, "void");
        }
        getEvaluator().switchScope();
        return returned;
    }
}
