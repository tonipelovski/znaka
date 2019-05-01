package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations.BasicOperation;
import com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations.DoubleOper;
import com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations.FloatOper;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.CannotEvaluate;
import com.znaka.Exceptions.UnknownVariable;
import com.znaka.Exceptions.WrongType;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.AssignAST;
import com.znaka.ParserStructures.VarAST;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AssignOper extends BaseExecuteOper {

    private List<BasicOperation> ls;

    public AssignOper(Evaluator eval) {
        super(AssignAST.class, eval);
        ls = new ArrayList<>();
//        ls.add(new IntOper());
        ls.add(new DoubleOper());
        ls.add(new FloatOper());
//        ls.add(new CharOper());
//        ls.add(new StringOper());
//        ls.add(new BoolOper());
    }

    @Override
    public DataVal exec(DefaultAST ast) throws CannotEvaluate, UnknownVariable, WrongType {
        DataVal ret = null;
        AssignAST ast1 = (AssignAST)ast;
        DataVal rightSide = getEvaluator().Eval(ast1.getRight());
        // create variable if not defined before
        // evaluate right side
        // assign right side to variable

        final String rightType = rightSide.getType();
        final String rightVal = rightSide.toString();
        String leftType = ((VarAST)ast1.getLeft()).getVariableType();
        leftType = leftType.isEmpty() ? rightType : leftType;
        final String varName = ast1.getLeft().getText();
        final HashSet<Variable> vars = getEvaluator().getVariables();


        ret = applyTypeToRightSide(leftType, rightSide);

        if(ret == null){
            //throw new CannotEvaluate("Cannot evaluate right side");
            ret = rightSide;
        }

        Variable var = new Variable<>(varName, ret, false);
        vars.remove(var);
        vars.add(var);



        return ret; // return right side
    }

    private DataVal applyTypeToRightSide(String type, DataVal right){
        for (BasicOperation operation : ls) {
            if(operation.getType().equals(type)){
                return operation.convert(right);
            }
        }
        return null;
    }
}
