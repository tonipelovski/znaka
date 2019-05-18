package com.znaka.EvaluatorStructures.ExecuteOperations;

import com.znaka.Evaluator;
import com.znaka.EvaluatorStructures.DataVal;
import com.znaka.EvaluatorStructures.Scope;
import com.znaka.EvaluatorStructures.Variable;
import com.znaka.Exceptions.EvaluatorException;
import com.znaka.ParserStructures.DefaultAST;
import com.znaka.ParserStructures.MainAST;
import com.znaka.ParserStructures.Statement.ConditionalsAST;
import com.znaka.ParserStructures.Statement.ElseConditionAST;
import com.znaka.ParserStructures.Statement.IfConditionAST;
import com.znaka.ParserStructures.Statement.LoopAST;

public class ConditionOper extends BaseExecuteOper {
    private Scope conditionalScope;
    private Scope originalScope;
    private boolean returnStatus;
    public ConditionOper(Evaluator eval) {
        super(ConditionalsAST.class, eval);
        conditionalScope = new Scope();
        originalScope = new Scope();
        returnStatus = false;
    }

    public boolean isReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(boolean returnStatus) {
        this.returnStatus = returnStatus;
    }

    @Override
    public DataVal exec(DefaultAST ast) throws EvaluatorException {

        /*originalScope.variables.addAll(getEvaluator().getCurrentScope().variables);
        originalScope.functions.addAll(getEvaluator().getCurrentScope().functions);*/
        originalScope = getEvaluator().getCurrentScope();

        conditionalScope.variables.addAll(getEvaluator().getCurrentScope().variables);
        conditionalScope.functions.addAll(getEvaluator().getCurrentScope().functions);
        getEvaluator().setCurrentScope(conditionalScope);
        if(ast instanceof IfConditionAST || ast instanceof ElseConditionAST) {

            ConditionalsAST ast1 = (ConditionalsAST) ast;
            DataVal result = new DataVal(true, "boolean");
            if(ast1.getCond() != null){
                result = this.getEvaluator().Eval(ast1.getCond().getAll_AST().get(0));
            }
            if (result != null) {
                getEvaluator().setLastReturnedValue(result);
            }
            Boolean cond_result = false;
            if (result.getType().equals("bool") || result.getType().equals("boolean")) {
                cond_result = (Boolean) result.getVal();
            }

            if (cond_result) {
                MainAST body = ast1.getBody();
                for (DefaultAST ast2 : body.getAll_AST()) {
                    if(ast2.getType().equals("conditional")){
                        ConditionOper conditionOper = new ConditionOper(getEvaluator());
                        conditionOper.exec(ast2);
                        if(conditionOper.isReturnStatus()){
                            setReturnStatus(true);
                            break;
                        }
                    }else if(ast2.getType().equals("return")){
                        DataVal body_result = this.getEvaluator().Eval(ast2);
                        if (body_result != null) {
                            getEvaluator().setLastReturnedValue(body_result);
                        }
                        returnStatus = true;
                        break;
                    }
                    else if(ast2.getType().equals("break")){
                        break;
                    }
                    else {
                        DataVal body_result = this.getEvaluator().Eval(ast2);
                        if (body_result != null) {
                            getEvaluator().setLastReturnedValue(body_result);
                        }
                    }
                }

            } else {
                if(ast1.getElse_cond() == null){
                    getEvaluator().setCurrentScope(originalScope);
                    return null;
                }
                if (ast1.getElse_cond().getCond() == null) {

                    MainAST body = ast1.getElse_cond().getBody();
                    for (DefaultAST ast2 : body.getAll_AST()) {
                        if(ast2.getType().equals("conditional")){
                            ConditionOper conditionOper = new ConditionOper(getEvaluator());
                            conditionOper.exec(ast2);
                            if(conditionOper.isReturnStatus()){
                                setReturnStatus(true);
                                break;
                            }
                        }else if(ast2.getType().equals("return")){
                            DataVal body_result = this.getEvaluator().Eval(ast2);
                            if (body_result != null) {
                                getEvaluator().setLastReturnedValue(body_result);
                            }
                            returnStatus = true;
                            break;
                        }
                        else if(ast2.getType().equals("break")){
                            break;
                        }else {
                            DataVal body_result = this.getEvaluator().Eval(ast2);
                            if (body_result != null) {
                                getEvaluator().setLastReturnedValue(body_result);
                            }
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
                    if(ast2.getType().equals("conditional")){
                        ConditionOper conditionOper = new ConditionOper(getEvaluator());
                        conditionOper.exec(ast2);
                        if(conditionOper.isReturnStatus()){
                            setReturnStatus(true);
                            break;
                        }
                    }else if(ast2.getType().equals("return")){
                        DataVal body_result = this.getEvaluator().Eval(ast2);
                        if (body_result != null) {
                            getEvaluator().setLastReturnedValue(body_result);
                        }
                        returnStatus = true;
                        break;
                    }
                    else if(ast2.getType().equals("break")){
                        break;
                    }else {
                        DataVal body_result = this.getEvaluator().Eval(ast2);
                        if (body_result != null) {
                            getEvaluator().setLastReturnedValue(body_result);
                        }
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
        for(Variable variable : originalScope.variables){
                for(Variable variable1 : conditionalScope.variables){
                    if(variable.getName().equals(variable1.getName())){
                        variable.setVal(variable1.getVal());

                    }
            }
        }
        getEvaluator().setCurrentScope(originalScope);
        return null;
    }
}
