package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.AssignAST;

public class AssignOper extends BaseExecuteOper {

    public AssignOper(Evaluator eval) {
        super(AssignAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) {
        DataVal ret = null;
        System.out.println("Executing assign operation...");
        AssignAST ast1 = (AssignAST)ast;
        System.out.println("Operator: " + ast1.getOperator());
        System.out.println("Left side: " + ast1.getLeft());
        System.out.println("Right side: " + ast1.getRight());

        if(ast1.getRight().getType().equals("number")){
            ret = new DataVal<>(Float.parseFloat(ast1.getRight().getText()));
        }
        // create variable if not defined before
        // evaluate right side
        // assign right side to variable

        return ret; // return right side
    }
}
