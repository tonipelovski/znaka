package com.znaka.EvaluatorStructures.ExecuteOperations.BinaryOper;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.ExecuteOperations.BaseExecuteOper;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.CannotEvaluate;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.ExitException;
import com.znaka.Exceptions.UnknownVariable;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.*;
import com.znaka.ParserStructures.NumberAST;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BinaryOper extends BaseExecuteOper {
    public BinaryOper(Evaluator eval) {
        super(OperatorAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException, ExitException {
        DataVal ret = null;
        OperatorAST binaryOper = (OperatorAST) ast;

        DefaultAST right = binaryOper.getRight();
        DefaultAST left = binaryOper.getLeft();
        String leftType = "";
        String leftVal = "";
        if(left == null){
            NumberAST numberAST = new NumberAST("0");
            numberAST.setNumberType("integer");
            binaryOper.setLeft(numberAST);
        }
        leftType = binaryOper.getLeft().getType();
        leftVal = binaryOper.getLeft().getText();

        String rightType = "";
        String rightVal = "";

        rightType = binaryOper.getRight().getType();
        rightVal = binaryOper.getRight().getText();

        DataVal right_result = null;
        DataVal left_result = null;
        HashSet<Variable> vars = getEvaluator().getVariables();

        if(left instanceof OperatorAST || left instanceof VarAST || left instanceof FunctionCallAST){
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
                    left_result = new DataVal<>((int) lv, "int");
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

        if(right instanceof BasicOperators || right instanceof VarAST || right instanceof FunctionCallAST){
            right_result = this.getEvaluator().Eval(right);
            if(right_result != null){
                getEvaluator().setLastReturnedValue(right_result);
            }
        }else {
            if(rightType.equals("number")){
                rightType = ((NumberAST)binaryOper.getRight()).getNumberType();
                //ret = new DataVal<>(Float.parseFloat(ast1.getRight().getText()));
                double rv = Double.parseDouble(rightVal);
                if(rightType.equals("integer")){
                    right_result = new DataVal<>((int) rv, "int");
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
        List<String> compares = Arrays.asList(">", ">=", "<", "<=", "==");
        List<String> calculate_assigns = Arrays.asList("+=", "-=", "*=", "/=");

        List<String> booleans = Arrays.asList("||", "&&");

        if(compares.contains(binaryOper.getOperator())){
            return compare(left_result, right_result, binaryOper);
        }else if (booleans.contains(binaryOper.getOperator())){
            return boolean_calculation(left_result, right_result, binaryOper);
        }else if (calculate_assigns.contains(binaryOper.getOperator())){
            return calculate_assign(left_result, right_result, binaryOper);
        }
        return calculate(left_result, right_result, binaryOper);
    }

    private DataVal boolean_calculation(DataVal left_result, DataVal right_result, OperatorAST binaryOper) {
        Boolean left = Boolean.parseBoolean(String.valueOf(left_result.getVal()));
        Boolean right = Boolean.parseBoolean(String.valueOf(right_result.getVal()));
        if(binaryOper.getOperator().equals("&&")){
            return new DataVal(and(left, right), "boolean");
        }else if(binaryOper.getOperator().equals("||")){
            return new DataVal(or(left, right), "boolean");
        }
        return null;
    }

    private DataVal compare(DataVal left_result, DataVal right_result, OperatorAST binaryOper) {
        Double left_num = Double.parseDouble(String.valueOf(left_result.getVal()));
        Double right_num = Double.parseDouble(String.valueOf(right_result.getVal()));
        switch (binaryOper.getOperator()) {
            case ">":
                return new DataVal(greater(left_num, right_num), "boolean");
            case "<":
                return new DataVal(smaller(left_num, right_num), "boolean");
            case "<=":
                return new DataVal(smaller_equal(left_num, right_num), "boolean");
            case ">=":
                return new DataVal(greater_equal(left_num, right_num), "boolean");
            case "==":
            return new DataVal(equal(left_num, right_num), "boolean");
        }
        return null;
    }

    private Boolean equal(Double left_num, Double right_num) {
        return left_num.equals(right_num);
    }

    private Boolean greater_equal(Double left_num, Double right_num) {
        return left_num >= right_num;
    }

    private Boolean smaller_equal(Double left_num, Double right_num) {
        return left_num <= right_num;
    }

    private Boolean smaller(Double left_num, Double right_num) {
        return left_num < right_num;
    }

    private Boolean greater(Double left_num, Double right_num) {
        return left_num > right_num;
    }

    private DataVal calculate_assign(DataVal left, DataVal right, OperatorAST operatorAST) throws EvaluatorException, ExitException {
        Double left_num = Double.parseDouble(String.valueOf(left.getVal()));
        Double right_num = Double.parseDouble(String.valueOf(right.getVal()));

        if(left.getType().equals("int")){
            left_num = (double) left_num.intValue();
        }else if(left.getType().equals("float")){
            left_num = (double) left_num.floatValue();
        }
        if(right.getType().equals("int")){
            right_num = (double) right_num.intValue();
        }else if(right.getType().equals("float")){
            right_num = (double) right_num.floatValue();
        }

        Double result = null;

        if(operatorAST.getOperator().equals("+=")){
            result = add(left_num,right_num);
        }
        if(operatorAST.getOperator().equals("-=")){
            result = sub(left_num,right_num);
        }
        if(operatorAST.getOperator().equals("*=")){
            result = mul(left_num,right_num);
        }
        if(operatorAST.getOperator().equals("/=")){
            result = div(left_num,right_num);
        }
        DataVal to_assign = null;
        NumberAST numberAST = new NumberAST(null);

        //System.out.println("real: " + 10/3);
        if(left.getType().equals("int") && right.getType().equals("int")) {
            to_assign = new DataVal(result.intValue(), "int");
            numberAST.setNumberType("integer");
        }
        else if(left.getType().equals("float") || right.getType().equals("float")) {
            to_assign = new DataVal(result.floatValue(), "float");
            numberAST.setNumberType("float");
        }else{
            to_assign = new DataVal(result, "double");
            numberAST.setNumberType("double");
        }
        numberAST.setValue(String.valueOf(to_assign.getVal()));
        AssignAST assignAST = new AssignAST(operatorAST.getLeft(), numberAST);
        DataVal assign_result = this.getEvaluator().Eval(assignAST);
        getEvaluator().setLastReturnedValue(assign_result);
        return assign_result;
    }


    private DataVal calculate(DataVal left, DataVal right, OperatorAST operatorAST) throws CannotEvaluate, UnknownVariable{
        Double left_num = Double.parseDouble(String.valueOf(left.getVal()));
        Double right_num = Double.parseDouble(String.valueOf(right.getVal()));

        if(left.getType().equals("int")){
            left_num = (double) left_num.intValue();
        }else if(left.getType().equals("float")){
            left_num = (double) left_num.floatValue();
        }
        if(right.getType().equals("int")){
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
        if(left.getType().equals("int") && right.getType().equals("int")) {
            return new DataVal(result.intValue(), "int");
        }
        if(left.getType().equals("float") || right.getType().equals("float")) {
            return new DataVal(result.floatValue(), "float");
        }

        return new DataVal(result, "double");
    }

    private Boolean and(Boolean left_bool, Boolean right_bool) {

        return left_bool && right_bool;
    }

    private Boolean or(Boolean left_bool, Boolean right_bool) {

        return left_bool || right_bool;
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
