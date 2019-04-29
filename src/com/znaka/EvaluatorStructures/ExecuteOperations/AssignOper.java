package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.AssignAST;
import com.znaka.ParserStructures.NumberAST;

public class AssignOper extends BaseExecuteOper {

    public AssignOper(Evaluator eval) {
        super(AssignAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) {
        DataVal ret = null;
        //System.out.println("Executing assign operation...");
        AssignAST ast1 = (AssignAST)ast;
        //System.out.println("Operator: " + ast1.getOperator());
        //System.out.println("Left side: " + ast1.getLeft());
        //System.out.println("Right side: " + ast1.getRight());
        String rightType = ast1.getRight().getType();
        String rightVal = ast1.getRight().getText();
        String leftType = ast1.getLeft().getType();

        if(rightType.equals("number")){
            rightType = ((NumberAST)ast1.getRight()).getNumberType();
            //ret = new DataVal<>(Float.parseFloat(ast1.getRight().getText()));
            double nm = Float.parseFloat(rightVal);
            if(leftType.equals("int") || rightType.equals("integer")){
                ret = new DataVal<>((int)nm);
            }
            else if(leftType.equals("double")){
                ret = new DataVal<>(nm);
            }
            else if(leftType.equals("float") || rightType.equals("float")){
                ret = new DataVal<>((float)nm);
            }
        }

        if(rightType.equals("char") || leftType.equals("char")){
            ret = new DataVal<>(rightVal.charAt(0));
        }

        if(rightType.equals("string_literal") || leftType.equals("string")){
            ret = new DataVal<>(rightVal);
        }

        // create variable if not defined before
        // evaluate right side
        // assign right side to variable

        return ret; // return right side
    }
}
