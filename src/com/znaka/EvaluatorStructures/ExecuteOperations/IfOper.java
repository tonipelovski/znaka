package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Statement.IfConditionAST;

public class IfOper extends BaseExecuteOper {
    public IfOper(Evaluator eval) {
        super(IfConditionAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) {
        System.out.println("Executing if oper...");
        IfConditionAST ast1 = (IfConditionAST) ast;
        System.out.println(ast1.getType());
        System.out.println(ast1.getCond());
        System.out.println(ast1.getBody());
        // evaluate condition and check if it is true
        // execute body if it is true
        return null; // if is a statement does not return anything
    }
}
