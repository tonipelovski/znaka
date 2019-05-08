package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Functions.Function;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.FunctionDefAST;
import com.znaka.ParserStructures.VarAST;

import java.util.ArrayList;
import java.util.List;

public class FunctionDef extends BaseExecuteOper {
    public FunctionDef(Evaluator eval) {
        super(FunctionDefAST.class, eval);
    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException {
        FunctionDefAST fnAST = (FunctionDefAST)ast;
        List<Variable> args = new ArrayList<>();
        for (DefaultAST arg : fnAST.getArgs()) {
            final VarAST var = ((VarAST)arg);
            args.add(new Variable(var.getText(), new DataVal("", var.getVariableType()),
                    var.getAccessType().equals("non-var")));
        }
        Function fn = new Function(fnAST.getText(), fnAST.getRet_type(), args, fnAST.getBody().getAll_AST());
        getEvaluator().getFunctions().add(fn);
        return null;
    }
}
