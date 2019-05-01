package com.znaka.EvaluatorStructures.ExecuteOperations.UnaryOper;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.ExecuteOperations.BaseExecuteOper;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.CannotEvaluate;
import com.znaka.Exceptions.UnknownVariable;
import com.znaka.Exceptions.WrongType;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.OperatorAST;
import com.znaka.ParserStructures.Expression.UnaryOperatorAST;
import com.znaka.ParserStructures.NumberAST;
import com.znaka.ParserStructures.VarAST;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class UnaryOper extends BaseExecuteOper {
    public UnaryOper(Evaluator eval) {
        super(UnaryOperatorAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws UnknownVariable, CannotEvaluate, WrongType {
        UnaryOperatorAST unaryOperatorAST = (UnaryOperatorAST) ast;

        DefaultAST left = unaryOperatorAST.getLeft();
        String leftType = "";
        String leftVal = "";

        leftType = unaryOperatorAST.getLeft().getType();
        leftVal = unaryOperatorAST.getLeft().getText();
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
                leftType = ((NumberAST) unaryOperatorAST.getLeft()).getNumberType();
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
            if(leftType.equals("boolean")){
                if(leftVal.equals("true")){
                    left_result = new DataVal<>((boolean) true, "bool");
                }else{
                    left_result = new DataVal<>((boolean) false, "bool");
                }
            }
            if (leftType.equals("char")) {
                //error
            }

            if (leftType.equals("string_literal")) {
                //error
            }
        }
        List<String> booleans = Arrays.asList("!");
        if (booleans.contains(unaryOperatorAST.getOperator())){
            return boolean_calculation(left_result, unaryOperatorAST);
        }
        return calculate(left_result, unaryOperatorAST);
    }

    private DataVal boolean_calculation(DataVal left_result, UnaryOperatorAST unaryOperatorAST) {
        boolean left_num = false;
        if(left_result.getVal().equals(true)){
            left_num = true;
        }else{
            left_num = false;
        }
        System.out.println(left_result.getType() + " " + left_result.getVal());
        if(unaryOperatorAST.getOperator().equals("!")){
            return new DataVal(not(left_num), "boolean");
        }
        return null;
    }

    private Boolean not(boolean left_num) {
        return !left_num;
    }

    private DataVal calculate(DataVal left, UnaryOperatorAST unaryOperatorAST) {
        Double left_num = Double.parseDouble(String.valueOf(left.getVal()));
        if(left.getType().equals("int")){
            left_num = (double) left_num.intValue();
        }else if(left.getType().equals("float")){
            left_num = (double) left_num.floatValue();
        }
        Double result = null;
        if(unaryOperatorAST.getOperator().equals("++")){
            result = add(left_num);
        }
        if(unaryOperatorAST.getOperator().equals("--")){
            result = sub(left_num);
        }

        //System.out.println("real: " + 10/3);
        if(left.getType().equals("int")) {
            return new DataVal(result.intValue(), "int");
        }
        if(left.getType().equals("float")) {
            return new DataVal(result.floatValue(), "float");
        }

        return new DataVal(result, "double");
    }

    private Double add(Double left_num) {
        return left_num+1;
    }

    private Double sub(Double left_num) {
        return left_num-1;
    }



}
