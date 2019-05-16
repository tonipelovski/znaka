package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.UnknownVariable;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.VarAST;

import java.util.HashSet;

public class VarGetOper extends BaseExecuteOper {
    public VarGetOper(Evaluator eval) {
        super(VarAST.class, eval);
    }

    public static Variable findVar(HashSet<Variable> vars, String varName){
        return vars.stream().filter(o -> o.getName().equals(varName))
                .findFirst().orElse(null);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws UnknownVariable {
        VarAST ast1 = (VarAST)ast;
        String varName = ast1.getText();
        HashSet<Variable> vars = getEvaluator().getVariables();
        Variable var = findVar(vars, varName);
        if(var == null){
            throw new UnknownVariable("No such variable: " + varName);
        }
        // find the variable by name if it exists and return it's value
        return var.getVal();
    }
}
