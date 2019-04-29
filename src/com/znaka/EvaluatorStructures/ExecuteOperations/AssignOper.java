package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.CannotEvaluate;
import com.znaka.Exceptions.UnknownVariable;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.AssignAST;
import com.znaka.ParserStructures.VarAST;

import java.util.HashSet;

public class AssignOper extends BaseExecuteOper {

    public AssignOper(Evaluator eval) {
        super(AssignAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws CannotEvaluate, UnknownVariable {
        DataVal ret = null;
        //System.out.println("Executing assign operation...");
        AssignAST ast1 = (AssignAST)ast;
        //System.out.println("Operator: " + ast1.getOperator());
        //System.out.println("Left side: " + ast1.getLeft());
        //System.out.println("Right side: " + ast1.getRight());
        DataVal rightSide = getEvaluator().Eval(ast1.getRight());

        String rightType = rightSide.getType();
        String rightVal = rightSide.toString();
        String leftType = ((VarAST)ast1.getLeft()).getVariableType();
        String varName = ast1.getLeft().getText();
        HashSet<Variable> vars = getEvaluator().getVariables();



//            rightType = rightSide.getType();
            //ret = new DataVal<>(Float.parseFloat(ast1.getRight().getText()));
//            double nm = Double.parseDouble(rightVal);
            if(leftType.equals("int") || rightType.equals("integer")){
                ret = new DataVal<>(rightSide.getVal(), "integer");
            }
            else if(leftType.equals("double")){
                ret = new DataVal<>(Double.parseDouble(rightSide.toString()), "double");
            }
            else if(leftType.equals("float") || rightType.equals("float")){
                ret = new DataVal<>(Float.parseFloat(rightSide.toString()), "float");
            }


        if(rightType.equals("char") || leftType.equals("char")){
            ret = new DataVal<>(rightVal.charAt(0), "char");
        }

        if(rightType.equals("string_literal") || leftType.equals("string")){
            ret = new DataVal<>(rightVal, "string");
        }

        Variable var = new Variable<>(varName, ret, false);
        vars.remove(var);
        vars.add(var);

        // create variable if not defined before
        // evaluate right side
        // assign right side to variable

        return ret; // return right side
    }
}
