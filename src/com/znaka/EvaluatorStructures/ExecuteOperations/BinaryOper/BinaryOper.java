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
        if(left instanceof OperatorAST || left instanceof VarAST){
            left_result = this.getEvaluator().Eval(left);
            if(left_result != null){
                getEvaluator().setLastReturnedValue(left_result);
            }
        }else {
            if (leftType.equals("number")) {
                leftType = ((NumberAST) binaryOper.getRight()).getNumberType();
                //ret = new DataVal<>(Float.parseFloat(ast1.getRight().getText()));
                double lv = Double.parseDouble(leftVal);
                if (leftType.equals("int") || rightType.equals("integer")) {
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

        return calculate(left_result, right_result, binaryOper);
    }

    protected DataVal calculate(DataVal left, DataVal right, OperatorAST operatorAST) throws CannotEvaluate, UnknownVariable{
        if(operatorAST.getOperator().equals("+")){
            return add(left,right);
        }
        if(operatorAST.getOperator().equals("-")){
            return sub(left,right);
        }
        if(operatorAST.getOperator().equals("*")){
            return mul(left,right);
        }
        if(operatorAST.getOperator().equals("/")){
            return div(left,right);
        }
        return null;
    }

    public DataVal div(DataVal left, DataVal other){

        if(left.getVal() instanceof Integer && other.getVal() instanceof Integer){
            return new DataVal<Integer>((Integer)left.getVal() / (Integer)other.getVal(), "integer");
        }
        if(left.getVal() instanceof Double && other.getVal() instanceof Double){
            return new DataVal<Double>((Double) left.getVal() / (Double) other.getVal(), "double");
        }
        if(left.getVal() instanceof Float && other.getVal() instanceof Float){
            return new DataVal<Float>((Float) left.getVal() / (Float) other.getVal(), "float");
        }
        return null;
    }

    public DataVal sub(DataVal left, DataVal other){

        if(left.getVal() instanceof Integer && other.getVal() instanceof Integer){
            return new DataVal<Integer>((Integer)left.getVal() - (Integer)other.getVal(), "integer");
        }
        if(left.getVal() instanceof Double && other.getVal() instanceof Double){
            return new DataVal<Double>((Double) left.getVal() - (Double) other.getVal(), "double");
        }
        if(left.getVal() instanceof Float && other.getVal() instanceof Float){
            return new DataVal<Float>((Float) left.getVal() - (Float) other.getVal(), "float");
        }
        return null;
    }

    public DataVal add(DataVal left, DataVal other){

        if(left.getVal() instanceof Integer && other.getVal() instanceof Integer){
            return new DataVal<Integer>((Integer)left.getVal() + (Integer)other.getVal(), "integer");
        }
        if(left.getVal() instanceof Double && other.getVal() instanceof Double){
            return new DataVal<Double>((Double) left.getVal() + (Double) other.getVal(), "double");
        }
        if(left.getVal() instanceof Float && other.getVal() instanceof Float){
            return new DataVal<Float>((Float) left.getVal() + (Float) other.getVal(), "float");
        }
        return null;
    }

    public DataVal mul(DataVal left, DataVal other){

        if(left.getVal() instanceof Integer && other.getVal() instanceof Integer){
            return new DataVal<Integer>((Integer)left.getVal() * (Integer)other.getVal(), "integer");
        }
        if(left.getVal() instanceof Double && other.getVal() instanceof Double){
            return new DataVal<Double>((Double) left.getVal() * (Double) other.getVal(), "double");
        }
        if(left.getVal() instanceof Float && other.getVal() instanceof Float){
            return new DataVal<Float>((Float) left.getVal() * (Float) other.getVal(), "float");
        }
        return null;
    }
}
