package com.znaka.EvaluatorStructures.ExecuteOperations.BinaryOper;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.ExecuteOperations.BaseExecuteOper;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.CannotEvaluate;
import com.znaka.Exceptions.UnknownVariable;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.OperatorAST;
import com.znaka.ParserStructures.NumberAST;
import com.znaka.ParserStructures.VarAST;

import java.util.HashSet;

public class BinaryOper extends BaseExecuteOper {
    public BinaryOper(Evaluator eval) {
        super(OperatorAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws CannotEvaluate, UnknownVariable {
        DataVal ret = null;
        OperatorAST binaryOper = (OperatorAST) ast;

        DefaultAST right = binaryOper.getRight();
        DefaultAST left = binaryOper.getLeft();
        String rightType = "";
        String rightVal = "";
        String leftType = "";
        String leftVal = "";
        rightType = binaryOper.getRight().getType();
        rightVal = binaryOper.getRight().getText();
        leftType = binaryOper.getLeft().getType();
        leftVal = binaryOper.getLeft().getText();
        DataVal right_result = null;
        DataVal left_result = null;
        HashSet<Variable> vars = getEvaluator().getVariables();

        if(left instanceof OperatorAST || left instanceof VarAST){
            //System.out.println("true");
            left_result = this.getEvaluator().Eval(left);
            if(left_result != null){
                getEvaluator().setLastReturnedValue(left_result);
            }
        }else {
            if (leftType.equals("number")) {
                leftType = ((NumberAST) binaryOper.getLeft()).getNumberType();
                //ret = new DataVal<>(Float.parseFloat(ast1.getRight().getText()));
                double lv = Double.parseDouble(leftVal);
                if (leftType.equals("integer")) {
                    left_result = new DataVal<>((int) lv, "integer");
                } else if (leftType.equals("double")) {
                    left_result = new DataVal<>(lv, "double");
                } else if (leftType.equals("float")) {
                    left_result = new DataVal<>((float) lv, "float");
                }
            }

            if (leftType.equals("char")) {
                //error
            }

            if (leftType.equals("string_literal")) {
                //error
            }
        }

        if(right instanceof OperatorAST || right instanceof VarAST){
            right_result = this.getEvaluator().Eval(right);
            if(right_result != null){
                getEvaluator().setLastReturnedValue(right_result);
            }
        }else {
            if(rightType.equals("number")){
                rightType = ((NumberAST)binaryOper.getRight()).getNumberType();
                //ret = new DataVal<>(Float.parseFloat(ast1.getRight().getText()));
                double rv = Double.parseDouble(rightVal);
                if(rightType.equals("int") || rightType.equals("integer")){
                    right_result = new DataVal<>((int) rv, "integer");
                }
                else if(rightType.equals("double")){
                    right_result = new DataVal<>(rv, "double");
                }
                else if(rightType.equals("float")){
                    right_result = new DataVal<>((float) rv, "float");
                }
            }

            if(rightType.equals("char")){
                //error
            }

            if(rightType.equals("string_literal")){
                //error
            }

        }

        return calculate(left_result, right_result, binaryOper);
    }

    protected DataVal calculate(DataVal left, DataVal right, OperatorAST operatorAST) throws CannotEvaluate, UnknownVariable{
        Double left_num = Double.parseDouble(String.valueOf(left.getVal()));
        Double right_num = Double.parseDouble(String.valueOf(right.getVal()));
        if(left.getType().equals("integer")){
            left_num = (double) left_num.intValue();
        }else if(left.getType().equals("float")){
            left_num = (double) left_num.floatValue();
        }
        if(right.getType().equals("integer")){
            right_num = (double) right_num.intValue();
        }else if(right.getType().equals("float")){
            right_num = (double) right_num.floatValue();
        }
        Double result = null;
        if(operatorAST.getOperator().equals("+")){
            result = add(left_num,right_num);
        }
        if(operatorAST.getOperator().equals("-")){
            result = sub(left_num,right_num);
        }
        if(operatorAST.getOperator().equals("*")){
            result = mul(left_num,right_num);
        }
        if(operatorAST.getOperator().equals("/")){
            result = div(left_num,right_num);
        }
        //System.out.println("real: " + 10/3);
        if(left.getType().equals("integer") && right.getType().equals("integer")) {
            return new DataVal(result.intValue(), "integer");
        }
        if(left.getType().equals("float") || right.getType().equals("float")) {
            return new DataVal(result.floatValue(), "float");
        }

        return new DataVal(result, "double");
    }

    public double div(double left, double other){
        return left/other;
    }

    public double sub(double left, double other){
        return left - other;
    }

    public double add(double left, double other){
        return left + other;
    }

    public double mul(double left, double other){
        return left*other;
    }
}
