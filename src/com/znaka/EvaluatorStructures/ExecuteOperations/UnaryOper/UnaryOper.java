package com.znaka.EvaluatorStructures.ExecuteOperations.UnaryOper;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.ExecuteOperations.BaseExecuteOper;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.ExitException;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.AssignAST;
import com.znaka.ParserStructures.Expression.OperatorAST;
import com.znaka.ParserStructures.Expression.UnaryOperatorAST;
import com.znaka.ParserStructures.Expression.VarAST;
import com.znaka.ParserStructures.NumberAST;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class UnaryOper extends BaseExecuteOper {
    public UnaryOper(Evaluator eval) {
        super(UnaryOperatorAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException, ExitException {
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

        DataVal to_assign = null;
        NumberAST numberAST = new NumberAST(null);

        //System.out.println("real: " + 10/3);
        if(left.getType().equals("int")) {
            to_assign = new DataVal(result.intValue(), "int");
            numberAST.setNumberType("integer");
        }
        else if(left.getType().equals("float")) {
            to_assign = new DataVal(result.floatValue(), "float");
            numberAST.setNumberType("float");
        }else{
            to_assign = new DataVal(result, "double");
            numberAST.setNumberType("double");
        }
        numberAST.setValue(String.valueOf(to_assign.getVal()));
        AssignAST assignAST = new AssignAST(unaryOperatorAST.getLeft(), numberAST);
        DataVal assign_result = this.getEvaluator().Eval(assignAST);
        getEvaluator().setLastReturnedValue(assign_result);
        return assign_result;
    }

    private Double add(Double left_num) {
        return left_num+1;
    }

    private Double sub(Double left_num) {
        return left_num-1;
    }



}
