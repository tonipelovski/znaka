package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.ExecuteOperations.TypeConversionOperations.*;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.WrongType;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.AssignAST;
import com.znaka.ParserStructures.Expression.VarAST;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AssignOper extends BaseExecuteOper {

    private List<BasicOperation> ls;

    public AssignOper(Evaluator eval) {
        super(AssignAST.class, eval);
        ls = new ArrayList<>();
        ls.add(new IntOper());
        ls.add(new DoubleOper());
        ls.add(new FloatOper());
        ls.add(new CharOper());
        ls.add(new StringOper());
        ls.add(new BoolOper());
    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException {
        DataVal ret = null;
        AssignAST ast1 = (AssignAST)ast;
        DataVal rightSide = getEvaluator().Eval(ast1.getRight());
        // create variable if not defined before
        // evaluate right side
        // assign right side to variable

        final String rightType = rightSide.getType();
        final String rightVal = rightSide.toString();
        VarAST left = (VarAST) ast1.getLeft();
        String leftType = ((VarAST)ast1.getLeft()).getVariableType();
        leftType = leftType.isEmpty() ? rightType : leftType;
        final String varName = ast1.getLeft().getText();
        final boolean constant = ((VarAST) ast1.getLeft()).getAccessType().equals("non-var");
        final HashSet<Variable> vars = getEvaluator().getVariables();


        ret = applyTypeToRightSide(leftType, rightSide);

        if(ret == null){
            //throw new CannotEvaluate("Cannot evaluate right side");
            ret = rightSide;
        }
        Variable var = VarGetOper.findVar(vars, varName);
        if(var  == null){
            var = new Variable<>(varName, ret, constant);
            vars.add(var);
        }
        else{
            if(!left.getVariableType().isEmpty() || !left.getAccessType().isEmpty()){
                var = new Variable<>(varName, ret, constant);
                vars.remove(var);
                vars.add(var);
            }else {
                var.setVal(ret);
            }

        }



        return ret; // return right side
    }

    private DataVal applyTypeToRightSide(String type, DataVal right) throws WrongType {
        for (BasicOperation operation : ls) {
            if(operation.getType().equals(type)){
                return operation.convertWithCheck(right);
            }
        }
        return null;
    }
}
