package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Contracts.FunctionDefinitionASTInter;
import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.Exceptions.FunctionExistsException;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.VarAST;

import java.util.ArrayList;
import java.util.List;

public class FunctionDef extends BaseExecuteOper {
    public FunctionDef(Evaluator eval) {
        super(FunctionDefinitionASTInter.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException {
        FunctionDefinitionASTInter fnAST = (FunctionDefinitionASTInter) ast;
        List<Variable> args = new ArrayList<>();
        for (VarAST arg : fnAST.getArgs()) {
            if(arg == null){
                break;
            }
            args.add(new Variable(arg.getText(), new DataVal("", arg.getVariableType()),
                    arg.getAccessType().equals("non-var")));
        }
        Function fn = new Function(fnAST.getName(), fnAST.getRet_type(), args, fnAST.getBody());
        if(getEvaluator().getFunctions().contains(fn)){
            throw new FunctionExistsException("Function " + fn.getName() + " already exists.");
        }
        getEvaluator().getFunctions().add(fn);
        return null;
    }
}
