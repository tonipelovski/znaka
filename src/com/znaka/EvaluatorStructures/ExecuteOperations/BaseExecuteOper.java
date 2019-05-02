package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.ParserStructures.DefaultAST;

public abstract class BaseExecuteOper {
    private Class astClass;
    private Evaluator eval;

    public BaseExecuteOper(Class astClass, Evaluator eval) {
        this.astClass = astClass;
        this.eval = eval;
    }

    public Class getMatchClass() {
        return astClass;
    }

    public Evaluator getEvaluator() {
        return eval;
    }

    public abstract DataVal exec(DefaultAST ast) throws EvaluatorException;
}
