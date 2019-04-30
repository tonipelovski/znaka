package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.CannotEvaluate;
import com.znaka.Exceptions.UnknownVariable;
import com.znaka.Exceptions.WrongType;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.AssignAST;
import com.znaka.ParserStructures.VarAST;

import java.util.HashSet;

public class AssignOper extends BaseExecuteOper {

    public AssignOper(Evaluator eval) {
        super(AssignAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws CannotEvaluate, UnknownVariable, WrongType {
        DataVal ret = null;
        //System.out.println("Executing assign operation...");
        AssignAST ast1 = (AssignAST)ast;
        //System.out.println("Operator: " + ast1.getOperator());
        //System.out.println("Left side: " + ast1.getLeft());
        //System.out.println("Right side: " + ast1.getRight());
        DataVal rightSide = getEvaluator().Eval(ast1.getRight());

        final String rightType = rightSide.getType();
        final String rightVal = rightSide.toString();
        String leftType = ((VarAST)ast1.getLeft()).getVariableType();
        leftType = leftType.isEmpty() ? rightType : leftType;
        final String varName = ast1.getLeft().getText();
        final HashSet<Variable> vars = getEvaluator().getVariables();



//            rightType = rightSide.getType();
            //ret = new DataVal<>(Float.parseFloat(ast1.getRight().getText()));
//            double nm = Double.parseDouble(rightVal);
            if(leftType.equals("int")){
                /*if(!leftType.equals(rightType)){
                    throw new WrongType("Cannot assign " + rightType + " to int");
                }*/
                ret = new DataVal<>(rightSide.getVal(), "integer");
            }
            else if(leftType.equals("double")){
                ret = new DataVal<>(rightSide.getVal(), "double");
            }
            else if(leftType.equals("float")){
                ret = new DataVal<>(Float.parseFloat(rightVal), "float");
            }


        else if(leftType.equals("char")){
            ret = new DataVal<>(rightSide.getVal(), "char");
        }

        else if(leftType.equals("string")){
            ret = new DataVal<>(rightSide.getVal(), "string");
        }

        else if(leftType.equals("bool")){
            ret = new DataVal<>(rightSide.getVal(), "boolean");
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
