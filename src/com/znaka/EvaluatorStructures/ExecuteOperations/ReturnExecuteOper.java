package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
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
        return getEvaluator().Eval(ast1.getToReturn());
    }
}
