package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Scope;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.Expression.VarAST;
import com.znaka.ParserStructures.MainAST;
import com.znaka.ParserStructures.Statement.ConditionalsAST;
import com.znaka.ParserStructures.Statement.ElseConditionAST;
import com.znaka.ParserStructures.Statement.IfConditionAST;
import com.znaka.ParserStructures.Statement.LoopAST;

public class ConditionOper extends BaseExecuteOper {
    private Scope conditionalScope;
    private Scope originalScope;
    public ConditionOper(Evaluator eval) {
        super(ConditionalsAST.class, eval);
        conditionalScope = new Scope();
        originalScope = new Scope();

    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException {

        originalScope.variables.addAll(getEvaluator().getMainScope().variables);
        originalScope.functions.addAll(getEvaluator().getMainScope().functions);
        //originalScope = eval.getMainScope();

        conditionalScope.variables = getEvaluator().getCurrentScope().variables;
        conditionalScope.functions = getEvaluator().getCurrentScope().functions;
        getEvaluator().setCurrentScope(conditionalScope);
        if(ast instanceof IfConditionAST || ast instanceof ElseConditionAST) {
            System.out.println("Condition");

            ConditionalsAST ast1 = (ConditionalsAST) ast;
            DataVal result = this.getEvaluator().Eval(ast1.getCond().getAll_AST().get(0));
            if (result != null) {
                getEvaluator().setLastReturnedValue(result);
            }
            Boolean cond_result = false;
            if (result.getType().equals("boolean")) {
                cond_result = (Boolean) result.getVal();
            }
            if (cond_result) {
                MainAST body = ast1.getBody();
                for (DefaultAST ast2 : body.getAll_AST()) {
                    DataVal body_result = this.getEvaluator().Eval(ast2);
                    if (body_result != null) {
                        getEvaluator().setLastReturnedValue(body_result);
                    }
                }
            } else {
                if(ast1.getElse_cond() == null){
                    return null;
                }
                if (ast1.getElse_cond().getCond() == null) {

                    MainAST body = ast1.getElse_cond().getBody();
                    for (DefaultAST ast2 : body.getAll_AST()) {
                        DataVal body_result = this.getEvaluator().Eval(ast2);
                        if (body_result != null) {
                            getEvaluator().setLastReturnedValue(body_result);
                        }
                    }
                }else{
                    this.getEvaluator().Eval(ast1.getElse_cond());

                }
            }
        }
        if(ast instanceof LoopAST) {
            LoopAST ast1 = (LoopAST) ast;
            DataVal result = this.getEvaluator().Eval(ast1.getCond().getAll_AST().get(0));
            if (result != null) {
                getEvaluator().setLastReturnedValue(result);
            }
            Boolean cond_result = false;
            if (result.getType().equals("boolean")) {
                cond_result = (Boolean) result.getVal();
            }
            while (cond_result) {
                MainAST body = ast1.getBody();
                for (DefaultAST ast2 : body.getAll_AST()) {
                    DataVal body_result = this.getEvaluator().Eval(ast2);
                    if (body_result != null) {
                        getEvaluator().setLastReturnedValue(body_result);
                    }
                }
                result = this.getEvaluator().Eval(ast1.getCond().getAll_AST().get(0));
                if (result != null) {
                    getEvaluator().setLastReturnedValue(result);
                }
                cond_result = false;
                if (result.getType().equals("boolean")) {
                    cond_result = (Boolean) result.getVal();
                }
            }
        }

        getEvaluator().setCurrentScope(originalScope);
        getEvaluator().setMainScope(originalScope);
        return null;
    }
}
